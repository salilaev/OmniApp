package com.salilaev.domain.useCase.news

import com.salilaev.domain.repository.NewsRepository
import javax.inject.Inject

class GetSavedNewsUseCase @Inject constructor(
    private val repository: NewsRepository
){
    suspend operator fun invoke() = repository.getSavedNews()
}