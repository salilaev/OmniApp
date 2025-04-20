package com.salilaev.domain.useCase.news

import com.salilaev.domain.checker.ConnectivityChecker
import com.salilaev.domain.news.NewsData
import com.salilaev.domain.repository.NewsRepository
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val connectivityChecker: ConnectivityChecker
) {
    operator fun invoke(category: String): Flow<NetworkResult<List<NewsData>>> = flow {
        emit(NetworkResult.Loading)

        val localNews = repository.getLocalAllNewsByCategory(category)

        if (connectivityChecker.isNetworkAvailable()) {
            try {
                val remoteNews = repository.getRemoteAllNewsByCategory(category)
                repository.deleteAllNewsByCategory(category)
                repository.saveNews(remoteNews)
                emit(NetworkResult.Success(remoteNews))
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
}