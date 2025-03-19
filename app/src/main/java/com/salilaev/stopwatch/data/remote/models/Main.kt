package com.salilaev.stopwatch.data.remote.models

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Float
)
