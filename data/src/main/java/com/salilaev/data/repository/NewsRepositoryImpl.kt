package com.salilaev.data.repository

import com.salilaev.data.local.room.dao.NewsDao
import com.salilaev.data.local.room.entity.NewsEntity
import com.salilaev.data.mapper.toNewsData
import com.salilaev.data.mapper.toNewsEntity
import com.salilaev.data.remote.api.news.NewsService
import com.salilaev.data.remote.checker.ConnectivityChecker
import com.salilaev.data.remote.models.newsdata.toNewsEntity
import com.salilaev.domain.news.NewsData
import com.salilaev.domain.repository.NewsRepository
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao,
    private val connectivityChecker: ConnectivityChecker
) : NewsRepository {

    override fun getUnSavedNews(category: String): Flow<NetworkResult<List<NewsData>>> = flow {
        emit(NetworkResult.Loading)

        val localNews = newsDao.getAllNewsByCategory(category)

        if (connectivityChecker.isNetworkAvailable()) {
            try {
                val response = newsService.getNews(category = category)

                val newsEntityList = response.articles.map { it.toNewsEntity(category) }

                newsDao.deleteAllUnsavedNews(category)
                newsDao.insertNews(newsEntityList)

                emit(NetworkResult.Success(newsEntityList.map { it.toNewsData() }))

            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        } else {
            if (localNews.isEmpty()) {
                emit(NetworkResult.Error(Exception("No internet connection and no local news.")))
            } else {
                emit(NetworkResult.Success(localNews.map { it.toNewsData() }))
            }
        }
    }

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

    override suspend fun deleteNewsByUrl(url: String) {
        newsDao.deleteNewsByUrl(url)
    }
}