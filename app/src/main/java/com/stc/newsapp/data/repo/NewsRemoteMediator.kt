package com.stc.newsapp.data.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.stc.newsapp.data.local.NewsDB
import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.domain.entity.NewsResponse
import com.stc.newsapp.domain.entity.RemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsAPIService: NewsAPIService,
    private val db: NewsDB,
    private val listener: LoadingListener
) :
    RemoteMediator<Int, NewsResponse>() {
    private val STARTING_PAGE_INDEX = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsResponse>
    ): MediatorResult {
        listener.isLoading(true)
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                listener.isLoading(false)
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = newsAPIService.getLatestHeadLines(page = page)
            val endOfList = response.newsResponse.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDAO().clearAll()
                    db.newsDao().clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfList) null else page + 1
                val keys = response.newsResponse.map {
                    RemoteKeys(it.id.toString(), prevKey, nextKey)
                }
                db.remoteKeysDAO().insertRemote(keys)
                db.newsDao().saveNewsData(response.newsResponse)
            }
            listener.isLoading(false)
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (e: IOException) {
            listener.isLoading(false)
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            listener.isLoading(false)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, NewsResponse>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = db.withTransaction {
                    db.remoteKeysDAO().getAllKeys().lastOrNull()
                }
                Log.d("REMOTE KEY : APPEND",remoteKeys.toString())
                val nextKey = remoteKeys?.nextKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, NewsResponse>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { news -> db.remoteKeysDAO().getRemoteKeys(news.id.toString()) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, NewsResponse>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { newsId ->
                    db.remoteKeysDAO().getRemoteKeys(newsId.toString())
                }
            }
        }
    }
}