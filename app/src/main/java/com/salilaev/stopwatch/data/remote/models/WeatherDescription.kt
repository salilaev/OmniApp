package com.salilaev.stopwatch.data.remote.models

import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
