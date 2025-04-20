package com.salilaev.data.mapper

import com.salilaev.data.local.room.entity.NewsEntity
import com.salilaev.data.remote.models.newsdata.Article
import com.salilaev.domain.news.NewsData

internal fun NewsEntity.toNewsData() = NewsData(
    id = id,
    title = title,
    description = description,
    content = content,
    publishedAt = publishedAt,
    author = author,
    url = url,
    urlToImage = urlToImage,
    category = category,
    saved = saved
)

internal fun NewsData.toNewsEntity() = NewsEntity(
    id = id,
    title = title,
    description = description,
    content = content,
    publishedAt = publishedAt,
    author = author,
    url = url,
    urlToImage = urlToImage,
    category = category,
    saved = saved
)

fun Article.toNewsData(category: String): NewsData {
    return NewsData(
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