package com.stc.newsapp.domain.entity

import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("articles")
    val newsResponse: List<NewsResponse>
)