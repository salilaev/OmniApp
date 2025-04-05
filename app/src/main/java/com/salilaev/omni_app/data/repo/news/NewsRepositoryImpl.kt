package com.salilaev.omni_app.data.repo.news

import com.salilaev.omni_app.data.local.room.dao.NewsDao
import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.data.remote.api.news.NewsService
import com.salilaev.omni_app.data.remote.checker.ConnectivityChecker
import com.salilaev.omni_app.data.remote.models.newsdata.toNewsEntity
import com.salilaev.omni_app.data.remote.response.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao,
    private val connectivityChecker: ConnectivityChecker
) : NewsRepository {

    override fun getUnSavedNews(category: String): Flow<NetworkResult<List<NewsEntity>>> = flow {
        emit(NetworkResult.Loading)

        val localNews = newsDao.getAllNewsByCategory(category)

        if (connectivityChecker.isNetworkAvailable()) {
            try {
                val response = newsService.getNews(category = category)

                val newsEntityList = response.articles.map { it.toNewsEntity(category) }

                newsDao.deleteAllUnsavedNews(category)
                newsDao.insertNews(newsEntityList)

                emit(NetworkResult.Success(newsEntityList))

            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        } else {
            if (localNews.isEmpty()) {
                emit(NetworkResult.Error(Exception("No internet connection and no local news.")))
            } else {
                emit(NetworkResult.Success(localNews))
            }
        }
    }

    override suspend fun getSavedNews(): Flow<NetworkResult<List<NewsEntity>>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val savedNews = newsDao.getAllSavedNews()

                if (savedNews.isEmpty()) {
                    emit(NetworkResult.Success(emptyList()))
                } else {
                    emit(NetworkResult.Success(savedNews))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        }
    }

    override suspend fun saveNews(news: NewsEntity) {
        newsDao.saveNews(news)
    }

    override suspend fun deleteNewsByUrl(url: String) {
        newsDao.deleteNewsByUrl(url)
    }
}