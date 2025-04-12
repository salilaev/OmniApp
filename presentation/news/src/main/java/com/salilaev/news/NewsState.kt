package com.salilaev.news

import com.salilaev.domain.news.NewsData

data class NewsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val news: List<NewsData> = emptyList(),
    val selectedCategory: String = "General"
)