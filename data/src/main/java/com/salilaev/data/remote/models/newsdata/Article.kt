package com.salilaev.data.remote.models.newsdata

import com.google.gson.annotations.SerializedName
import com.salilaev.data.local.room.entity.NewsEntity

data class Article(
    @SerializedName("author") val author: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null
)

fun Article.toNewsEntity(category: String): NewsEntity {
    return NewsEntity(
        title = this.title ?: "",
        description = this.description ?: "",
        content = this.content ?: "",
        publishedAt = this.publishedAt ?: "",
        author = this.author ?: "",
        url = this.url ?: "",
        urlToImage = this.urlToImage ?: "",
        category = category
    )
}