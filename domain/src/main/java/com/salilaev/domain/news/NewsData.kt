package com.salilaev.domain.news

data class NewsData(
    val id: Int = 0,
    val title: String? = "",
    val description: String? = "",
    val content: String? = "",
    val publishedAt: String? = "",
    val author: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val category: String? = "",
    val saved: Boolean = false
)