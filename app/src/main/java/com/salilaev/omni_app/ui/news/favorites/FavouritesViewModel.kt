package com.salilaev.omni_app.ui.news.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.data.remote.response.NetworkResult
import com.salilaev.omni_app.domain.usecases.DeleteByUrlUseCase
import com.salilaev.omni_app.domain.usecases.GetAllSavedNewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getAllSavedNews: GetAllSavedNewUseCase,
    private val deleteByUrl: DeleteByUrlUseCase
) : ViewModel() {

    private val _savedNews = MutableStateFlow<List<NewsEntity>>(emptyList())
    val savedNews: StateFlow<List<NewsEntity>> get() = _savedNews

    init {
        getSavedNews()
    }

    private fun getSavedNews() {
        viewModelScope.launch {
            getAllSavedNews().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Success -> {
                        _savedNews.value = result.data
                    }
                    is NetworkResult.Error -> {

                    }
                }
            }
        }
    }

    fun deleteNews(newsUrl: String) {
        viewModelScope.launch {
            deleteByUrl(newsUrl)
            getSavedNews()
        }
    }
}