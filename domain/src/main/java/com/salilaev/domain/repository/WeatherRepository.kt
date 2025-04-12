package com.salilaev.domain.repository



import com.salilaev.domain.models.WeatherData
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(): Flow<NetworkResult<WeatherData>>
}

//See sealed class
//Create sealed class representing 3 network response states (loading, success, error)
//See how to handle response 3 states (loading, success, error) using sealed class in repository
