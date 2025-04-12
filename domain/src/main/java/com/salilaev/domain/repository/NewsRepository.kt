package com.salilaev.domain.repository

import com.salilaev.domain.news.NewsData
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getUnSavedNews(category: String): Flow<NetworkResult<List<NewsData>>>

    suspend fun getSavedNews(): Flow<NetworkResult<List<NewsData>>>

    suspend fun saveNews(news: NewsData)

    suspend fun deleteNewsByUrl(url: String)
}