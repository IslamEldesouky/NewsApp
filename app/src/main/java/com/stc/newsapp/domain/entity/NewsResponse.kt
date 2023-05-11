package com.stc.newsapp.domain.entity

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("media")
    val image: String
)