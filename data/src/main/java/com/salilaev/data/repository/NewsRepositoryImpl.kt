package com.salilaev.data.repository

import com.salilaev.data.local.room.dao.NewsDao
import com.salilaev.data.mapper.toNewsData
import com.salilaev.data.mapper.toNewsEntity
import com.salilaev.data.remote.api.news.NewsService
import com.salilaev.domain.news.NewsData
import com.salilaev.domain.repository.NewsRepository
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao
) : NewsRepository {

    override suspend fun getSavedNews(): Flow<NetworkResult<List<NewsData>>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val savedNews = newsDao.getAllSavedNews()

                if (savedNews.isEmpty()) {
                    emit(NetworkResult.Success(emptyList()))
                } else {
                    emit(NetworkResult.Success(savedNews.map { it.toNewsData() }))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        }
    }

    override suspend fun saveNews(news: NewsData) {
        newsDao.saveNews(news.toNewsEntity())
    }

    override suspend fun saveNews(news: List<NewsData>) {
        newsDao.insertNews(news.map { it.toNewsEntity() })
    }

    override suspend fun deleteNewsByUrl(url: String) {
        newsDao.deleteNewsByUrl(url)
    }

    override suspend fun getLocalAllNewsByCategory(category: String): List<NewsData> {
        return newsDao.getAllNewsByCategory(category).map { it.toNewsData() }
    }

    override suspend fun getRemoteAllNewsByCategory(category: String): List<NewsData> {
        return newsService.getNews(category = category).articles.map { it.toNewsData(category) }
    }

    override suspend fun deleteAllNewsByCategory(category: String) {
        newsDao.deleteAllUnsavedNews(category)

    }

}