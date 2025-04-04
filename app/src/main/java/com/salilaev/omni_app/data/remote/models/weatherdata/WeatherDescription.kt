package com.salilaev.omni_app.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
