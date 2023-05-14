package com.medicalapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stc.newsapp.Utils
import com.stc.newsapp.data.local.NewsDAO
import com.stc.newsapp.data.local.NewsDB
import com.stc.newsapp.data.local.RemoteKeysDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NewsDBQueryTests {
    private lateinit var newsDAO: NewsDAO
    private lateinit var remoteKeysDao: RemoteKeysDao
    lateinit var db: NewsDB

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDB::class.java).allowMainThreadQueries()
            .build()
        newsDAO = db!!.newsDao()
        remoteKeysDao = db!!.remoteKeysDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db!!.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertAndReadNewsData() = testScope.runTest {
        val newsDataResponse = Utils().getMockNewsResponse()
        newsDAO.saveNewsData(newsDataResponse.newsResponse)
        checkNotNull(newsDAO.getNewsData())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertAndReadRemoteKeys() = testScope.runTest {
        val remoteKeysList = Utils().getMockRemoteKeys()
        remoteKeysDao.insertRemote(remoteKeysList)
        checkNotNull(remoteKeysDao.getRemoteKeys("2"))
    }
}