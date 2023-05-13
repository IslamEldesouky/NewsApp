package com.stc.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stc.newsapp.domain.entity.NewsData


@Database(
    entities = [NewsData::class],
    version = DBConfig.Constants.DB_VERSION,
    exportSchema = false
)
abstract class NewsDB : RoomDatabase() {

    abstract fun newsDao(): NewsDAO
}