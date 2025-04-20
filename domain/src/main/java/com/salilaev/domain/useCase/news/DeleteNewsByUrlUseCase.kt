package com.salilaev.domain.useCase.news

import com.salilaev.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteNewsByUrlUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(url: String) = repository.deleteNewsByUrl(url)
}