package com.stc.newsapp.data.local

object Queries {

    const val SELECT_NEWS_DATA_FROM_TABLE =
        "SELECT * FROM ${DBConfig.Constants.NEWS_TABLE_NAME}"
    const val DELETE_NEWS_FROM_TABLE = "DELETE FROM ${DBConfig.Constants.NEWS_TABLE_NAME} "
    const val SELECT_FROM_REMOTE_KEYS_TABLE = "SELECT * FROM ${DBConfig.Constants.REMOTE_KEYS_TABLE_NAME} WHERE repoId = :id"
    const val SELECT_ALL_FROM_REMOTE_KEYS_TABLE = "SELECT * FROM ${DBConfig.Constants.REMOTE_KEYS_TABLE_NAME}"
    const val DELETE_FROM_REMOTE_KEYS_TABLE = "DELETE FROM ${DBConfig.Constants.REMOTE_KEYS_TABLE_NAME} "

}