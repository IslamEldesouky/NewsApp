package com.stc.newsapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stc.newsapp.data.local.DBConfig

@Entity(tableName = DBConfig.Constants.NEWS_TABLE_NAME)
data class NewsResponse(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("media")
    val image: String?
)