package com.stc.newsapp.presentation.di

import com.stc.newsapp.data.local.NewsDAO
import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.data.repo.NewsRepositoryImpl
import com.stc.newsapp.domain.repo.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: NewsAPIService, newsDAO: NewsDAO): NewsRepository {
        return NewsRepositoryImpl(apiService, newsDAO)
    }
}