package com.efuntikov.newsapp.domain.service.news

import com.efuntikov.newsapp.domain.repository.NewsDatabase
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.datasource.NewsApiDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(
    private val database: NewsDatabase,
    private val newsApiDataSource: NewsApiDataSource
) : NewsService {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun observeEverything() = database.newsDao().observeNews()

    override suspend fun fetchEverything() {
        newsApiDataSource.getEverything(object : NewsCallback {
            override fun onSuccess(result: List<NewsItemEntity>) {
                coroutineScope.launch(Dispatchers.IO) {
                    database.newsDao().insertAll(result)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                TODO("Not yet implemented")
            }
        })
    }
}