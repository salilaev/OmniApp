package com.salilaev.omni_app.data.remote.response

sealed class NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: Throwable) : NetworkResult<Nothing>()
}