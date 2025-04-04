package com.salilaev.omni_app.domain.usecases

import com.salilaev.omni_app.data.local.room.entity.NewsEntity
import com.salilaev.omni_app.data.repo.news.NewsRepository
import javax.inject.Inject

class SaveNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(news: NewsEntity) {
        newsRepository.saveNews(news)
    }
}