package com.stc.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.entity.NewsResponse
import com.stc.newsapp.domain.entity.RemoteKeys


@Database(
    entities = [NewsResponse::class, RemoteKeys::class],
    version = DBConfig.Constants.DB_VERSION,
    exportSchema = false
)
abstract class NewsDB : RoomDatabase() {

    abstract fun newsDao(): NewsDAO
    abstract fun remoteKeysDAO(): RemoteKeysDao
}