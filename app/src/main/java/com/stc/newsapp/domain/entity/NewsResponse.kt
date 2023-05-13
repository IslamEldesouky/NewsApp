package com.stc.newsapp.domain.entity

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("media")
    val image: String
)