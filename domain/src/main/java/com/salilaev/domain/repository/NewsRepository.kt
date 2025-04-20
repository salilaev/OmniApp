package com.salilaev.domain.repository

import com.salilaev.domain.news.NewsData
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getSavedNews(): Flow<NetworkResult<List<NewsData>>>

    suspend fun saveNews(news: NewsData)

    suspend fun deleteNewsByUrl(url: String)

    suspend fun getLocalAllNewsByCategory(category: String): List<NewsData>

    suspend fun getRemoteAllNewsByCategory(category: String): List<NewsData>

    suspend fun deleteAllNewsByCategory(category: String)

    suspend fun saveNews(news: List<NewsData>)
}