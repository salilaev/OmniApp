package com.salilaev.omni_app.data.repo.weather

import com.salilaev.omni_app.data.remote.api.weatherapi.WeatherService
import com.salilaev.omni_app.data.remote.models.weatherdata.WeatherResponse
import com.salilaev.omni_app.data.remote.response.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {

    override fun getWeather(): Flow<NetworkResult<WeatherResponse>> = flow {
        emit(NetworkResult.Loading)
        val data = weatherService.getWeather()
        emit(NetworkResult.Success(data))

    }.catch {
        emit(NetworkResult.Error(it))

    }.flowOn(Dispatchers.IO)

}