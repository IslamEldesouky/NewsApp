package com.stc.newsapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stc.newsapp.data.local.Queries.DELETE_NEWS_FROM_TABLE
import com.stc.newsapp.data.local.Queries.SELECT_NEWS_DATA_FROM_TABLE
import com.stc.newsapp.domain.entity.NewsResponse


@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewsData(newsList: List<NewsResponse>)

    @Query(SELECT_NEWS_DATA_FROM_TABLE)
    fun getNewsData(): PagingSource<Int, NewsResponse>

    @Query(DELETE_NEWS_FROM_TABLE)
    fun clearAll()
}