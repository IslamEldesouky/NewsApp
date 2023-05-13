package com.stc.newsapp.data.remote

import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.entity.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET(APIConfig.Constants.API_ENDPOINT)
    suspend fun getLatestHeadLines(
        @Query(APIConfig.Constants.QUERY_COUNTRY_NAME) country: String = APIConfig.Constants.QUERY_COUNTRY_VALUE,
        @Query(APIConfig.Constants.QUERY_TOPIC_NAME) topic: String = APIConfig.Constants.QUERY_TOPIC_VALUE
    ) : NewsData
}