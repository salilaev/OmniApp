package com.salilaev.stop_watch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.domain.repository.TimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val timeRepository: TimeRepository) :
    ViewModel() {

    private val _isTimerStarted: MutableLiveData<Boolean> = MutableLiveData(false)
    val isTimerStarted: LiveData<Boolean> = _isTimerStarted

    private val _time: MutableLiveData<Long> = MutableLiveData(0)
    val time: LiveData<Long> = _time

    private var timerJob: Job? = null

    private var timerValue: Long = 0

    init {
        viewModelScope.launch {
            val savedTime = timeRepository.getTime()
            if (savedTime != 0L) {
                _time.postValue(savedTime)
                timerValue = savedTime
            }
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            _isTimerStarted.postValue(true)
            while (true) {
                delay(1000)
                timerValue++
                _time.postValue(timerValue)
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        _isTimerStarted.value = false
    }

    fun reset() {
        timerJob?.cancel()
        _time.postValue(0)
        timerValue = 0
        stopTimer()
        viewModelScope.launch {
            timeRepository.setTime(0)
        }
    }

    fun saveTime() {
        viewModelScope.launch {
            timeRepository.setTime(timerValue)
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveTime()
    }
}