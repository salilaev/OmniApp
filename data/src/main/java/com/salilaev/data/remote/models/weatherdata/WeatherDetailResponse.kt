package com.salilaev.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class WeatherDetailResponse(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Float
)
