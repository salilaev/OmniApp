package com.salilaev.news.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.domain.news.NewsData
import com.salilaev.domain.result.NetworkResult
import com.salilaev.domain.useCase.news.DeleteNewsByUrlUseCase
import com.salilaev.domain.useCase.news.GetSavedNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteNewsByUrlUseCase: DeleteNewsByUrlUseCase
) : ViewModel() {

    private val _savedNews = MutableStateFlow<List<NewsData>>(emptyList())
    val savedNews: StateFlow<List<NewsData>> get() = _savedNews

    init {
        getSavedNews()
    }

     fun getSavedNews() {
        viewModelScope.launch {
            getSavedNewsUseCase().collect { result ->
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
            deleteNewsByUrlUseCase(newsUrl)
            getSavedNews()
        }
    }
}