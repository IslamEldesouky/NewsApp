package com.stc.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stc.newsapp.data.local.Queries.DELETE_FROM_REMOTE_KEYS_TABLE
import com.stc.newsapp.data.local.Queries.SELECT_ALL_FROM_REMOTE_KEYS_TABLE
import com.stc.newsapp.data.local.Queries.SELECT_FROM_REMOTE_KEYS_TABLE
import com.stc.newsapp.domain.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemote(list: List<RemoteKeys>)

    @Query(SELECT_FROM_REMOTE_KEYS_TABLE)
    fun getRemoteKeys(id:String) : RemoteKeys

    @Query(SELECT_ALL_FROM_REMOTE_KEYS_TABLE)
    fun getAllKeys(): List<RemoteKeys>

    @Query(DELETE_FROM_REMOTE_KEYS_TABLE)
    fun clearAll()
}