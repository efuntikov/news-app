package com.efuntikov.newsapp.domain.service.news

import com.efuntikov.newsapp.domain.repository.NewsDatabase
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(
    private val database: NewsDatabase
) : NewsService {
    private val newsApiDataSource = NewsApiDataSource()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun getTopNews(newsCallback: NewsCallback) =
        newsApiDataSource.getTopHeadlines(newsCallback = newsCallback)

    override fun observeEverything() = database.newsDao().observeNews()

    override suspend fun fetchEverything() {
        newsApiDataSource.getEverything(object : NewsCallback {
            override fun onSuccess(result: List<NewsItemEntity>) {
                coroutineScope.launch(Dispatchers.IO) {
                    database.newsDao().insertAll(result)
                }
            }
        })
    }
}