package com.stc.newsapp.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.stc.newsapp.data.local.NewsDB
import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.data.repo.LoadingListener
import com.stc.newsapp.data.repo.NewsRemoteMediator
import com.stc.newsapp.domain.entity.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsAPIService: NewsAPIService, private val db: NewsDB
) : ViewModel(), LoadingListener {

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _items: MutableLiveData<Flow<PagingData<NewsResponse>?>> = MutableLiveData()
    val items: LiveData<Flow<PagingData<NewsResponse>?>> = _items

    init {
        getItems()
    }
     private fun getItems() {
         try{
             _items.value = Pager(
                 config = PagingConfig(10, enablePlaceholders = false),
                 pagingSourceFactory = { db.newsDao().getNewsData() },
                 remoteMediator = NewsRemoteMediator(newsAPIService, db = db, this)
             ).flow.cachedIn(viewModelScope)
         }catch (e:java.lang.Exception){
             Log.d("ERROR", e.message.toString())
         }
    }

    override fun isLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}