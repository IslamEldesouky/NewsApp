package com.stc.newsapp.data.repo

import android.util.Log
import com.stc.newsapp.data.local.NewsDAO
import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.repo.NewsRepository

class NewsRepositoryImpl(private val newsAPIService: NewsAPIService, private val newsDAO: NewsDAO) :
    NewsRepository {
    override suspend fun getNewsResponse(): NewsData {
        return getNewsDataDb()
    }

    private suspend fun getNewsDataAPI(): NewsData {
        lateinit var newsDataResponse: NewsData
        try {
            val response = newsAPIService.getLatestHeadLines()
            newsDataResponse = response
        } catch (e: java.lang.Exception) {
            Log.i("Err", e.message.toString())
        }
        return newsDataResponse
    }

    private suspend fun getNewsDataDb(): NewsData {
        lateinit var newsDataResponse: NewsData
        try {
            val response = newsDAO.getNewsData()
            if (response != null) {
                newsDataResponse = response
                return newsDataResponse
            } else {
                newsDataResponse = getNewsDataAPI()
                newsDAO.saveNewsData(newsDataResponse)
            }
        } catch (e: Exception) {
            Log.i("Err", e.message.toString())
        }
        return newsDataResponse
    }
}