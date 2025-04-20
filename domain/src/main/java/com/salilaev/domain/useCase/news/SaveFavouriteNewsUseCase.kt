package com.salilaev.domain.useCase.news

import com.salilaev.domain.news.NewsData
import com.salilaev.domain.repository.NewsRepository
import javax.inject.Inject

class SaveFavouriteNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(news: NewsData) = newsRepository.saveNews(news)
}