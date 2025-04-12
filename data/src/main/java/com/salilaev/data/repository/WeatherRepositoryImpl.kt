package com.salilaev.data.repository

import com.salilaev.data.mapper.toWeatherData
import com.salilaev.domain.repository.WeatherRepository
import com.salilaev.data.remote.api.weatherapi.WeatherService
import com.salilaev.domain.models.WeatherData
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {

    override fun getWeather(): Flow<NetworkResult<WeatherData>> = flow {
        emit(NetworkResult.Loading)
        val data = weatherService.getWeather().toWeatherData()
        emit(NetworkResult.Success(data))

    }.catch {
        emit(NetworkResult.Error(it))

    }.flowOn(Dispatchers.IO)

}