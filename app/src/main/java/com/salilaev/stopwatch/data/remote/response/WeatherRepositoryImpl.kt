package com.salilaev.stopwatch.data.remote.response

import com.salilaev.stopwatch.data.remote.api.WeatherService
import com.salilaev.stopwatch.data.remote.models.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getWeather(): Flow<WeatherResponse> {
        TODO("Not yet implemented")
    }

}