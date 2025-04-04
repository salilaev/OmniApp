package com.salilaev.omni_app.data.repo.weather


import com.salilaev.omni_app.data.remote.models.weatherdata.WeatherResponse
import com.salilaev.omni_app.data.remote.response.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(): Flow<NetworkResult<WeatherResponse>>
}

//See sealed class
//Create sealed class representing 3 network response states (loading, success, error)
//See how to handle response 3 states (loading, success, error) using sealed class in repository
