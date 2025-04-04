package com.salilaev.omni_app.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<WeatherDescription>,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("sys") val sys: Sys
)



