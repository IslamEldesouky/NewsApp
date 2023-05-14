package com.stc.newsapp

import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.entity.NewsResponse

class Utils {

    fun getMockNewsResponse(): NewsData {
        return NewsData(listOf(
            NewsResponse(1, "STC awarded best company of the yeat", ""),
            NewsResponse(2, "STC awarded best company of the yeat", ""),
            NewsResponse(3, "STC awarded best company of the yeat", ""),
            NewsResponse(4, "STC awarded best company of the yeat", ""),
            NewsResponse(5, "STC awarded best company of the yeat", ""),
            NewsResponse(6, "STC awarded best company of the yeat", "")))
    }

    fun getMockNewsResponseEmpty(): NewsData{
        return NewsData(emptyList())
    }
}