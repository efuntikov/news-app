package com.efuntikov.newsapp.domain.service.news

import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.flow.Flow

interface NewsCallback {
    fun onSuccess(result: List<NewsItemEntity>)
}

interface NewsService {
    fun getTopNews(newsCallback: NewsCallback)

    fun observeEverything(): Flow<List<NewsItemEntity>>

    suspend fun fetchEverything()
}
