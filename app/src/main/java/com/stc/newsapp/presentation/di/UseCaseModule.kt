package com.stc.newsapp.presentation.di

import com.stc.newsapp.domain.repo.NewsRepository
import com.stc.newsapp.domain.usecase.GetNewsUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetMedicalDataUseCase(newsRepository: NewsRepository): GetNewsUsecase {
        return GetNewsUsecase(newsRepository)
    }
}