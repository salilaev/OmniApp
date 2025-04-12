package com.salilaev.data.di

import com.salilaev.data.repository.NewsRepositoryImpl
import com.salilaev.data.repository.TimeRepositoryImpl
import com.salilaev.data.repository.WeatherRepositoryImpl
import com.salilaev.domain.repository.NewsRepository
import com.salilaev.domain.repository.TimeRepository
import com.salilaev.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindTimeRepository(timeRepositoryImpl: TimeRepositoryImpl): TimeRepository

    @[Binds Singleton]
    fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @[Binds Singleton]
    fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

}
