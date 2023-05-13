package com.stc.newsapp.domain.repo

import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.entity.NewsResponse

interface NewsRepository {

    suspend fun getNewsResponse() : NewsData
}