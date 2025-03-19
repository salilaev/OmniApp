package com.salilaev.stopwatch.ui.weather

data class WeatherState(
    val temperature: Double = 0.0,
    val weatherDescription: String = "",
    val wind: Float = 0.0f,
    val wet: Float = 0.0f,
)
