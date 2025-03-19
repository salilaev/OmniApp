package com.salilaev.stopwatch.data.remote.response

import com.salilaev.stopwatch.data.remote.models.WeatherResponse

sealed class NetworkResult{
    data object Loading : NetworkResult()
    data class Success(val data: WeatherResponse?) : NetworkResult()
    data class Error(val message: String) : NetworkResult()
}