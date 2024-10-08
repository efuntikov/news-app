package com.efuntikov.newsapp.domain.service.tophead

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.dao.NewsFeed
import com.efuntikov.newsapp.domain.service.news.NewsCallback

interface TopHeadNewsService {
    fun getTopNews(newsCallback: NewsCallback)
    fun observeTopNewsByCategory(category: TopNewsCategory): NewsFeed
}
