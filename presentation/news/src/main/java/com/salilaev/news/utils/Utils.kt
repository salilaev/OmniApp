package com.salilaev.news.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatPublishedDateLegacy(dateString: String?): String {
    if (dateString.isNullOrBlank()) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString) ?: return ""

        val outputFormat = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale("eng"))
        outputFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun formatAuthor(author: String?): String {
    val maxAuthorLength = 30
    return if ((author?.length ?: 0) > maxAuthorLength) {
        author?.take(maxAuthorLength) + "..."
    } else {
        author ?: ""
    }
}
