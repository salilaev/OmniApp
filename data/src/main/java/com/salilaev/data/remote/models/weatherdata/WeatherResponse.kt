package com.salilaev.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main") val main: WeatherDetailResponse,
    @SerializedName("weather") val weather: List<WeatherDescriptionResponse>,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("sys") val sys: SunsetResponse
)



