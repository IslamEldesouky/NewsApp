package com.stc.newsapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stc.newsapp.domain.entity.NewsResponse

class Converters {
    @TypeConverter
    fun fromNewsList(news: List<NewsResponse>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<NewsResponse>>() {}.type
        return gson.toJson(news, type)
    }

    @TypeConverter
    fun toNewsList(json: String?): List<NewsResponse>? {
        if (json != null && json.isEmpty()) return null
        val gson = Gson()
        val type = object : TypeToken<List<NewsResponse>>() {}.type
        return gson.fromJson(json, type)
    }
}