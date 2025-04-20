package com.salilaev.data.repository

import com.salilaev.data.mapper.toWeatherData
import com.salilaev.domain.repository.WeatherRepository
import com.salilaev.data.remote.api.weatherapi.WeatherService
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getWeather() = weatherService.getWeather().toWeatherData()
}