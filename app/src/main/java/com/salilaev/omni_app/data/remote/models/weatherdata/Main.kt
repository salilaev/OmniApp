package com.salilaev.omni_app.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Float
)
