package com.efuntikov.newsapp.domain.service.tophead

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.NewsDatabase
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.datasource.Language
import com.efuntikov.newsapp.domain.service.datasource.NewsApiDataSource
import com.efuntikov.newsapp.domain.service.news.NewsCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadNewsServiceImpl @Inject constructor(
    private val database: NewsDatabase,
    private val newsApiDataSource: NewsApiDataSource
) : TopHeadNewsService {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun observeTopNewsByCategory(category: TopNewsCategory) =
        database.newsDao().observeNewsByCategory(category = category.name.lowercase())

    override fun getTopNews(newsCallback: NewsCallback) =
        TopNewsCategory.entries.forEach { category ->
            newsApiDataSource.getTopHeadlines(
                newsCallback = object : NewsCallback {
                    override fun onSuccess(result: List<NewsItemEntity>) {
                        coroutineScope.launch(Dispatchers.IO) {
                            database.newsDao().insertAll(result)
                        }
                    }

                    override fun onFailure(throwable: Throwable?) {
                        TODO("Not yet implemented")
                    }
                },
                category = category,
                pageSize = 20,
                language = Language.EN
            )
        }
}
