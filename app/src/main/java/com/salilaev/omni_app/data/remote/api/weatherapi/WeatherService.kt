package com.salilaev.omni_app.data.remote.api.weatherapi

import com.salilaev.omni_app.data.remote.models.weatherdata.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "359492d433566b426960f920c4edff4a"

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String = "Tashkent",
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric",
    ): WeatherResponse

}

