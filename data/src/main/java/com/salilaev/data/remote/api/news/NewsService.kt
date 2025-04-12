package com.salilaev.data.remote.api.news

import com.salilaev.data.remote.models.newsdata.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "5fb98e9b7d50431d9de07cf2bb6b2709"

interface NewsService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("category") category: String = "general",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}
