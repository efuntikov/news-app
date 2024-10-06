package com.efuntikov.newsapp.domain.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE id = :newsItemId")
    fun observeNewsItemById(newsItemId: String): Flow<NewsItemEntity>

    @Query("SELECT * FROM news")
    fun observeNews(): Flow<List<NewsItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsItem: NewsItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsItems: List<NewsItemEntity>)

    @Query("DELETE FROM news")
    fun deleteAll()
}