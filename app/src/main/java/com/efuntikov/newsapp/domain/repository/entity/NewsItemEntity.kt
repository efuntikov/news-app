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
    val imageUrl: String?,
    @ColumnInfo(name = "category")
    val category: String?
) {
    fun getKey() = id
    fun getType() = "news"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsItemEntity

        if (title != other.title) return false
        if (textContent != other.textContent) return false
        if (author != other.author) return false
        if (imageUrl != other.imageUrl) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + textContent.hashCode()
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        return result
    }

}
