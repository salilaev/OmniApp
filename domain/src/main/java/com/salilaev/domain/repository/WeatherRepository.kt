package com.salilaev.domain.repository

import com.salilaev.domain.models.WeatherData

interface WeatherRepository {
    suspend fun getWeather(): WeatherData
}
