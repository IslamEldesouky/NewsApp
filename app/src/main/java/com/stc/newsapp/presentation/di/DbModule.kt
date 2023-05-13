package com.stc.newsapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.stc.newsapp.data.local.DBConfig
import com.stc.newsapp.data.local.NewsDAO
import com.stc.newsapp.data.local.NewsDB
import com.stc.newsapp.data.local.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDB =
        Room.databaseBuilder(
            context,
            NewsDB::class.java,
            DBConfig.Constants.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(database: NewsDB): NewsDAO =
        database.newsDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: NewsDB): RemoteKeysDao =
        database.remoteKeysDAO()
}