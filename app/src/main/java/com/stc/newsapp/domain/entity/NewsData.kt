package com.stc.newsapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.stc.newsapp.data.local.Converters
import com.stc.newsapp.data.local.DBConfig.Constants.NEWS_TABLE_NAME

data class NewsData(
    @SerializedName("articles")
    @field:TypeConverters(Converters::class)
    val newsResponse: List<NewsResponse>
)