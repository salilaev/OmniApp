package com.salilaev.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class SunsetResponse (
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)