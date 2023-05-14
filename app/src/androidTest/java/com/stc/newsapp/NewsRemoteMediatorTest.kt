package com.stc.newsapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.stc.newsapp.data.local.NewsDAO
import com.stc.newsapp.data.local.NewsDB
import com.stc.newsapp.data.local.RemoteKeysDao
import com.stc.newsapp.data.remote.NewsAPIService
import com.stc.newsapp.data.repo.LoadingListener
import com.stc.newsapp.data.repo.NewsRemoteMediator
import com.stc.newsapp.domain.entity.NewsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsRemoteMediatorTest {

    @Mock
    private lateinit var newsAPIService: NewsAPIService

    @Mock
    private lateinit var db: NewsDB
    private lateinit var remoteKeysDao: RemoteKeysDao
    private lateinit var newsDAO: NewsDAO

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDB::class.java).allowMainThreadQueries()
            .build()
        remoteKeysDao = db.remoteKeysDAO()
        newsDAO = db.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Mock
    private lateinit var listener: LoadingListener

    private var remoteMediator: NewsRemoteMediator? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setup() {
        remoteMediator = NewsRemoteMediator(newsAPIService, db, listener)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun initializeshouldreturnLAUNCH_INITIAL_REFRESH() {
        testScope.runTest {
            val result = remoteMediator?.initialize()
            assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, result)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = testScope.runTest() {
        val newsDataResponse = Utils().getMockNewsResponse()
        Mockito.lenient().`when`(newsAPIService.getLatestHeadLines(page = 1))
            .thenReturn(newsDataResponse)
        val remoteMediator = NewsRemoteMediator(
            newsAPIService,
            db,
            listener
        )
        val pagingState = PagingState<Int, NewsResponse>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
    }
}