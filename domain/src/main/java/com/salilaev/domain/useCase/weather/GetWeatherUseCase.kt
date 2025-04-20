package com.salilaev.domain.useCase.weather

import com.salilaev.domain.models.WeatherData
import com.salilaev.domain.repository.WeatherRepository
import com.salilaev.domain.result.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<NetworkResult<WeatherData>> = flow {
        emit(NetworkResult.Loading)
        try {
            val weatherData = weatherRepository.getWeather()
            emit(NetworkResult.Success(weatherData))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}