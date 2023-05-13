package com.stc.newsapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stc.newsapp.data.local.DBConfig.Constants.REMOTE_KEYS_TABLE_NAME

@Entity(tableName = REMOTE_KEYS_TABLE_NAME)
data class RemoteKeys(
    @PrimaryKey()
    val repoId:String,
    val prevKey:Int?,
    val nextKey:Int?
)