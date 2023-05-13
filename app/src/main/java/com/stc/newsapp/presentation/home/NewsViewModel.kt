package com.stc.newsapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stc.newsapp.domain.entity.NewsData
import com.stc.newsapp.domain.entity.NewsResponse
import com.stc.newsapp.domain.usecase.GetNewsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsUsecase) : ViewModel() {

    private val _newsResponse: MutableStateFlow<NewsData?> = MutableStateFlow(null)
    val newsResponse: StateFlow<NewsData?> = _newsResponse

    fun getNewsResponse() {
        viewModelScope.launch {
            try {
                _newsResponse.value = getNewsUseCase.getNewsResponse()
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }
}