package com.salilaev.data.di

import com.salilaev.data.remote.api.news.NewsService
import com.salilaev.data.remote.api.weatherapi.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val WEATHER = "weather"
    private const val NEWS = "news"

    @Named(WEATHER)
    @[Provides Singleton]
    fun providesWeatherRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Named(NEWS)
    @[Provides Singleton]
    fun provideNewsRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @[Provides Singleton]
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    }

    @[Provides Singleton]
    fun provideWeatherService(@Named(WEATHER) retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @[Provides Singleton]
    fun provideNewsService(@Named(NEWS) retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}