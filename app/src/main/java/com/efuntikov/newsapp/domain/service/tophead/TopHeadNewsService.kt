package com.efuntikov.newsapp.domain.service.tophead

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.dao.NewsFeed
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.news.NewsCallback
import kotlinx.coroutines.flow.Flow

interface TopHeadNewsService {
    fun fetchTopNews()
    fun fetchTopNews(category: TopNewsCategory)
    fun observeTopNewsByCategory(category: TopNewsCategory): NewsFeed
    fun observeNewsItem(newsItemId: Long): Flow<NewsItemEntity>
}
