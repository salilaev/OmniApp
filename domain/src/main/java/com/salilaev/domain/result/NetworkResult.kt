package com.salilaev.domain.result

sealed class NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: Throwable) : NetworkResult<Nothing>()
}