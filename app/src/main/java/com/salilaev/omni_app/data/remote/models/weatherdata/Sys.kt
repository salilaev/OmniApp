package com.salilaev.omni_app.data.remote.models.weatherdata

import com.google.gson.annotations.SerializedName

data class Sys (
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)