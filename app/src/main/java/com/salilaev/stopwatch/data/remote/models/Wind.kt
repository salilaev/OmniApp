package com.salilaev.stopwatch.data.remote.models

import com.google.gson.annotations.SerializedName


data class Wind(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val degree: Int
)