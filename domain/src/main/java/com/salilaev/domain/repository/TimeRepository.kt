package com.salilaev.domain.repository

interface TimeRepository {
    suspend fun getTime(): Long
    suspend fun setTime(time: Long)
}