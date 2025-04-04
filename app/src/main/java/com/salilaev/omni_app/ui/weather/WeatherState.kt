package com.salilaev.omni_app.ui.weather

data class WeatherState(
    val weatherDescription: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val iconId: String? = null,
    val details: List<WeatherDetail> = emptyList(),
    val temperature: Double = 0.0,
    val isNight: Boolean = false
)