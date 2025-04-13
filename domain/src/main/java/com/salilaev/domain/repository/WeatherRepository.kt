package com.salilaev.domain.repository

import com.salilaev.domain.models.WeatherData
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(): Flow<NetworkResult<WeatherData>>
}
