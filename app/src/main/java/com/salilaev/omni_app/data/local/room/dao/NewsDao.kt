package com.salilaev.omni_app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salilaev.omni_app.data.local.room.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("SELECT * FROM news WHERE category = :category and saved = 0")
    suspend fun getAllNewsByCategory(category: String): List<NewsEntity>

    @Query("DELETE FROM news WHERE saved = 0 and category = :category")
    suspend fun deleteAllUnsavedNews(category: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(news: NewsEntity)

    @Query("SELECT * FROM news WHERE saved = 1")
    suspend fun getAllSavedNews(): List<NewsEntity>

    @Query("DELETE FROM news WHERE url = :newsUrl")
    suspend fun deleteNewsByUrl(newsUrl: String)

}