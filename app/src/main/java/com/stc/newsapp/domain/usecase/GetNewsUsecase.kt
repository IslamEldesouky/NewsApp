package com.stc.newsapp.domain.usecase

import com.stc.newsapp.domain.repo.NewsRepository

class GetNewsUsecase(private val newsRepository: NewsRepository) {

    suspend fun getNewsResponse() = newsRepository.getNewsResponse()
}