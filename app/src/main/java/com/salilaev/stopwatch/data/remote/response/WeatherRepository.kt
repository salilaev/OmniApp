package com.salilaev.stopwatch.data.remote.response


import com.salilaev.stopwatch.data.remote.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(): Flow<WeatherResponse>
}

//See sealed class
//Create sealed class representing 3 network response states (loading, success, error)
//See how to handle response 3 states (loading, success, error) using sealed class in repository
