package com.efuntikov.newsapp.domain.news

import com.efuntikov.newsapp.model.NewsItemModel

interface NewsCallback {
    fun onSuccess(result: List<NewsItemModel>)
}

class NewsService {
    private val newsApiDataSource = NewsApiDataSource()

    fun getTopNews(newsCallback: NewsCallback) =
        newsApiDataSource.getTopHeadlines(newsCallback = newsCallback)

    fun getEverything(newsCallback: NewsCallback) =
        newsApiDataSource.getEverything(newsCallback = newsCallback)
}
