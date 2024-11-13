package com.efuntikov.newsapp.domain.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.flow.Flow

typealias NewsFeed = Flow<List<NewsItemEntity>>

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE id = :newsItemId")
    fun observeNewsItemById(newsItemId: Long): Flow<NewsItemEntity>

    @Query("SELECT * FROM news WHERE category IS NULL ORDER BY id ASC")
    fun observeNews(): NewsFeed

    @Query("SELECT COUNT(*) FROM news WHERE category IS NULL")
    fun countNewsItems(): Int

    @Query("SELECT * FROM news WHERE category = :category")
    fun observeNewsByCategory(category: String): NewsFeed

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(newsItem: NewsItemEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(newsItems: List<NewsItemEntity>)

    @Query("DELETE FROM news")
    fun deleteAll()
}