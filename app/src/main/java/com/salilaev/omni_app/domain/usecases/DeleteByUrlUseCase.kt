package com.salilaev.omni_app.domain.usecases

import com.salilaev.omni_app.data.repo.news.NewsRepository
import javax.inject.Inject

class DeleteByUrlUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(newsUrl: String) {
        newsRepository.deleteNewsByUrl(newsUrl)
    }
}