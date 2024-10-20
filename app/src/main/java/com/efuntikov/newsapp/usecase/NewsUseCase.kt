package com.efuntikov.newsapp.usecase

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.dao.NewsFeed
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun observeEverything(): NewsFeed
    suspend fun fetchEverything()
    fun fetchTopNews()
    fun fetchTopNews(category: TopNewsCategory)
    fun observeTopNewsByCategory(category: TopNewsCategory): NewsFeed
    fun observeNewsItem(newsItemId: Long): Flow<NewsItemEntity>
}
