package com.salilaev.omni_app.ui.news.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.domain.usecases.DeleteByUrlUseCase
import com.salilaev.omni_app.domain.usecases.GetAllSavedNewUseCase
import com.salilaev.omni_app.domain.usecases.SaveNewsUseCase
import com.salilaev.omni_app.data.remote.response.NetworkResult
import com.salilaev.omni_app.ui.news.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val deleteByUrl: DeleteByUrlUseCase,
    private val getSavedNews: GetAllSavedNewUseCase,
    private val saveNews: SaveNewsUseCase
) : ViewModel() {

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> = _url

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _savedNews = MutableStateFlow<List<NewsEntity>>(emptyList())
    val savedNews: StateFlow<List<NewsEntity>> = _savedNews.asStateFlow()

    init {
        loadSavedNews()
    }

    fun updateUrl(newUrl: String) {
        _url.value = newUrl
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun toggleSavedNews(news: NewsEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            if (isNewsSaved(news.url?: "")) {
                deleteByUrl(news.url?: "")
            } else {
                saveNews(news)
            }
            loadSavedNews()
        }
    }

    private fun isNewsSaved(url: String): Boolean {
        return _savedNews.value.any { it.url == url }
    }

    private fun loadSavedNews() {
        viewModelScope.launch {
            getSavedNews().collect { result ->
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