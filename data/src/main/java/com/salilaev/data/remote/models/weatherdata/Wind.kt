package com.salilaev.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName


data class Wind(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val degree: Int
)