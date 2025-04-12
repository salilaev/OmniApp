package com.salilaev.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = "",
    val description: String? = "",
    val content: String? = "",
    val publishedAt: String? = "",
    val author: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val category: String? = "",
    val saved: Boolean = false
)