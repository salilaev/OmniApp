package com.salilaev.omni_app.di

import com.salilaev.omni_app.data.repo.news.NewsRepository
import com.salilaev.omni_app.data.repo.news.NewsRepositoryImpl
import com.salilaev.omni_app.data.repo.weather.WeatherRepository
import com.salilaev.omni_app.data.repo.weather.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Singleton Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[Singleton Binds]
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}