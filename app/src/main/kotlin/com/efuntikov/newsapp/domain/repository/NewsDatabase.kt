package com.efuntikov.newsapp.domain.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.efuntikov.newsapp.domain.repository.dao.NewsDao
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(
    entities = [
        NewsItemEntity::class
    ], version = 3
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    suspend fun clearNews() = withContext(Dispatchers.IO) {
        newsDao().deleteAll()
    }

    suspend fun clear() {
        clearNews()
    }
}
