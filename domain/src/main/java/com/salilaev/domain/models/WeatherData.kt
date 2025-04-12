package com.salilaev.domain.models

data class WeatherData(
    val detail: WeatherDetail,
    val description: List<WeatherDescription>,
    val wind: WeatherWind,
    val sunsetData: WeatherSunData
)
