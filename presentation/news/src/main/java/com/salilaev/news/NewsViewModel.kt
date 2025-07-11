package com.salilaev.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.domain.result.NetworkResult
import com.salilaev.domain.useCase.news.GetNewsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
) : ViewModel() {

    private val _newsState = MutableStateFlow(NewsState())
    val newsState = _newsState.asStateFlow()

    fun getCurrentNews(category: String) {
        viewModelScope.launch {
            getNewsByCategoryUseCase(category).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _newsState.value = _newsState.value.copy(
                            isLoading = true,
                            isError = false
                        )
                    }

                    is NetworkResult.Success -> {
                        _newsState.value = _newsState.value.copy(
                            news = result.data,
                            isLoading = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _newsState.value = _newsState.value.copy(
                            isError = true,
                            isLoading = false,
                            errorMessage = result.message.message,
                            news = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun onCategorySelected(category: String) {
        _newsState.value = _newsState.value.copy(
            selectedCategory = category,
            isLoading = true
        )
        getCurrentNews(category)
    }
}
