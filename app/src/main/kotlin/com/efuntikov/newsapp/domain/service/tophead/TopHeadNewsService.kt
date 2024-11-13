package com.efuntikov.newsapp.domain.service.tophead

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.datasource.Language
import com.efuntikov.newsapp.domain.repository.dao.NewsFeed
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.flow.Flow

interface TopHeadNewsService {
    fun fetchTopNews()
    suspend fun fetchTopNews(category: TopNewsCategory, language: Language)
    fun observeTopNewsByCategory(category: TopNewsCategory): NewsFeed
    fun observeNewsItem(newsItemId: Long): Flow<NewsItemEntity>
}
