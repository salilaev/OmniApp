package com.salilaev.omni_app.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salilaev.omni_app.data.remote.response.NetworkResult
import com.salilaev.omni_app.data.repo.weather.WeatherRepository
import com.salilaev.omni_app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _currentWeatherState = MutableStateFlow(WeatherState())
    val currentWeatherState get() = _currentWeatherState.asStateFlow()

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather() {
        weatherRepository.getWeather()
            .onEach { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        val error = when (result.message) {
                            is UnknownHostException -> "No internet"
                            else -> "Unknown error"
                        }
                        _currentWeatherState.update {
                            it.copy(
                                isError = true,
                                errorMessage = error,
                                isLoading = false
                            )
                        }
                    }

                    NetworkResult.Loading -> {
                        _currentWeatherState.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        _currentWeatherState.update {
                            it.copy(
                                details = listOf(
                                    WeatherDetail(
                                        iconId = R.drawable.image_icon_weather,
                                        title = "Weather",
                                        subtitle = result.data.weather[0].description
                                    ),
                                    WeatherDetail(
                                        iconId = R.drawable.image_icon_wind,
                                        title = "Wind",
                                        subtitle = "${result.data.wind.speed} m/s"
                                    ),
                                    WeatherDetail(
                                        iconId = R.drawable.image_icon_wet,
                                        title = "Wet",
                                        subtitle = "${result.data.main.humidity} %"
                                    ),
                                    WeatherDetail(
                                        iconId = R.drawable.image_icon_sunrice,
                                        title = "Sunrise",
                                        subtitle = formatTime(result.data.sys.sunrise)
                                    ),
                                    WeatherDetail(
                                        iconId = R.drawable.image_icon_sunset,
                                        title = "Sunset",
                                        subtitle = formatTime(result.data.sys.sunset)
                                    )
                                ),
                                weatherDescription = result.data.weather[0].description,
                                isLoading = false,
                                temperature = result.data.main.temp,
                                isNight = isDayTime(result.data.sys.sunrise, result.data.sys.sunset),
                                iconId = ("https://openweathermap.org/img/wn/${result.data.weather[0].icon}@2x.png"),
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun isDayTime(sunrise: Long, sunset: Long): Boolean {
        val currentTime = Calendar.getInstance().timeInMillis / 1000
        return currentTime in sunrise..sunset
    }

    private fun formatTime(timestamp: Long): String {
        return try {
            val date = java.util.Date(timestamp * 1000)
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            sdf.format(date)
        } catch (e: Exception) {
            "N/A"
        }
    }
}

