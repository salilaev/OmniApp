package com.salilaev.stopwatch.data.remote.api

import com.salilaev.stopwatch.data.remote.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String = "Tashkent",
        @Query("appid") apiKey: String = "359492d433566b426960f920c4edff4a",
        @Query("units") units: String = "metric",
    ): Response<WeatherResponse>

}

