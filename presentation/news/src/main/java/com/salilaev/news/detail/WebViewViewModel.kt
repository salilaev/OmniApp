package com.salilaev.news.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.domain.news.NewsData
import com.salilaev.domain.repository.NewsRepository
import com.salilaev.domain.result.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> = _url

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _savedNews = MutableStateFlow<List<NewsData>>(emptyList())
    val savedNews: StateFlow<List<NewsData>> = _savedNews.asStateFlow()

    init {
        loadSavedNews()
    }

    fun updateUrl(newUrl: String) {
        _url.value = newUrl
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun toggleSavedNews(news: NewsData) {
        viewModelScope.launch {
            _isLoading.value = true
            if (isNewsSaved(news.url?: "")) {
                repository.deleteNewsByUrl(news.url?: "")
            } else {
                repository.saveNews(news)
            }
            loadSavedNews()
        }
    }

    private fun isNewsSaved(url: String): Boolean {
        return _savedNews.value.any { it.url == url }
    }

    private fun loadSavedNews() {
        viewModelScope.launch {
            repository.getSavedNews().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data.let {
                            _savedNews.value = it
                            _isLoading.value = false
                        }
                    }
                    is NetworkResult.Error -> {
                        _savedNews.value = emptyList()
                        _isLoading.value = false
                    }
                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }
}