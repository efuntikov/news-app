package com.efuntikov.newsapp.domain.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val textContent: String,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?
) {
    fun getKey() = id
    fun getType() = "news"
}
