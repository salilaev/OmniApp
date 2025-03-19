package com.salilaev.stopwatch.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.stopwatch.data.remote.api.WeatherService
import com.salilaev.stopwatch.data.remote.response.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val mutableState = MutableStateFlow(WeatherState())
    val state get() = mutableState.asStateFlow()

    fun getCurrentWeather() {
        viewModelScope.launch {


            mutableState.update {
                it.copy(
        /*            temperature = weather,
                    weatherDescription = weather.weather,
                    wind = weather.wind.speed,
                    wet = weather.main.humidity*/
                )
            }


        }
    }
}


//See sealed class
//Create sealed class representing 3 network response states (loading, success, error)
//See how to handle response 3 states (loading, success, error) using sealed class in repository

