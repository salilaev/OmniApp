package com.salilaev.data.mapper

import com.salilaev.data.remote.models.weatherdata.SunsetResponse
import com.salilaev.data.remote.models.weatherdata.WeatherDescriptionResponse
import com.salilaev.data.remote.models.weatherdata.WeatherDetailResponse
import com.salilaev.data.remote.models.weatherdata.WeatherResponse
import com.salilaev.data.remote.models.weatherdata.Wind
import com.salilaev.domain.models.WeatherData
import com.salilaev.domain.models.WeatherDescription
import com.salilaev.domain.models.WeatherDetail
import com.salilaev.domain.models.WeatherSunData
import com.salilaev.domain.models.WeatherWind

internal fun WeatherResponse.toWeatherData() = WeatherData(
    detail = main.toWeatherDetail(),
    description = weather.map { it.toWeatherDescription() },
    wind = wind.toWeatherWind(),
    sunsetData = sys.toWeatherSunData()
)

internal fun WeatherDetailResponse.toWeatherDetail() = WeatherDetail(
    temp = temp,
    humidity = humidity
)

internal fun WeatherDescriptionResponse.toWeatherDescription() = WeatherDescription(
    description = description,
    icon = icon
)

internal fun Wind.toWeatherWind() = WeatherWind(
    speed = speed,
    degree = degree
)

internal fun SunsetResponse.toWeatherSunData() = WeatherSunData(
    sunrise = sunrise,
    sunset = sunset
)