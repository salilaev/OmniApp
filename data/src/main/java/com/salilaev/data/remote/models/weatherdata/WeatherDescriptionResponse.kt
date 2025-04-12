package com.salilaev.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class WeatherDescriptionResponse(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
