package com.salilaev.omni_app.ui.news

import com.salilaev.omni_app.data.local.room.entity.NewsEntity

data class NewsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val news: List<NewsEntity> = emptyList(),
    val selectedCategory:String = "General"
)