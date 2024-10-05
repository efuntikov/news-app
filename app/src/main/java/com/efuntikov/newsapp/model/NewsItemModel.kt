package com.efuntikov.newsapp.model

import java.util.UUID

data class NewsItemModel(
    val title: String,
    val textContent: String,
    val author: String?,
    private val id: String = UUID.randomUUID().toString()
) {
    fun getKey() = id
    fun getType() = "news"
}
