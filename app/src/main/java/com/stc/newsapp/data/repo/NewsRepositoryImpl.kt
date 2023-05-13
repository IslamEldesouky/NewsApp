package com.stc.newsapp.data.repo

import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.entity.NewsResponse
import com.stc.newsapp.domain.repo.NewsRepository

class NewsRepositoryImpl (private val newsAPIService: NewsAPIService) : NewsRepository {
    override suspend fun getNewsResponse(): NewsData{
        return newsAPIService.getLatestHeadLines()
    }
}