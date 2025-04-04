package com.salilaev.omni_app.domain.usecases

import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.data.remote.response.NetworkResult
import com.salilaev.omni_app.data.repo.news.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSavedNewUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Flow<NetworkResult<List<NewsEntity>>> {
         return repository.getSavedNews()
    }
}