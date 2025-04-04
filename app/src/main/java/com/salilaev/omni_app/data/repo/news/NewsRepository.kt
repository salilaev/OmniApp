package com.salilaev.omni_app.data.repo.news

import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.data.remote.response.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getUnSavedNews(category: String): Flow<NetworkResult<List<NewsEntity>>>

    suspend fun getSavedNews(): Flow<NetworkResult<List<NewsEntity>>>

    suspend fun saveNews(news: NewsEntity)

    suspend fun deleteNewsByUrl(url: String)
}