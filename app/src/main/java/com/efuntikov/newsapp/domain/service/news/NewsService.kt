package com.efuntikov.newsapp.domain.service.news

import com.efuntikov.newsapp.domain.repository.dao.NewsFeed
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity

interface NewsCallback {
    fun onSuccess(result: List<NewsItemEntity>)
    fun onFailure(throwable: Throwable?)
}

interface NewsService {

    fun observeEverything(): NewsFeed
    suspend fun fetchEverything()
}
