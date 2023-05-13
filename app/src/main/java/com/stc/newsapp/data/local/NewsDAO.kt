package com.stc.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stc.newsapp.data.local.Queries.SELECT_NEWS_DATA_FROM_TABLE
import com.stc.newsapp.domain.entity.NewsData


@Dao
interface NewsDAO {

    @Insert
    suspend fun saveNewsData(newsData: NewsData)

    @Query("$SELECT_NEWS_DATA_FROM_TABLE")
    suspend fun getNewsData() : NewsData
}