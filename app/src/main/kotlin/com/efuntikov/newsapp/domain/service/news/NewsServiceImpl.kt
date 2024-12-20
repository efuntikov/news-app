package com.efuntikov.newsapp.domain.service.news

import com.efuntikov.newsapp.domain.repository.NewsDatabase
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.datasource.Language
import com.efuntikov.newsapp.domain.datasource.NewsApiDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(
    private val database: NewsDatabase,
    private val newsApiDataSource: NewsApiDataSource
) : NewsService {
    companion object {
        private const val PAGE_SIZE = 20
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun observeEverything() = database.newsDao().observeNews()

    override fun observeNewsItemById(newsItemId: Long) =
        database.newsDao().observeNewsItemById(newsItemId = newsItemId)

    override suspend fun fetchEverything(language: Language, force: Boolean) {
        newsApiDataSource.getEverything(
            newsCallback = object : NewsCallback {
                override fun onSuccess(result: List<NewsItemEntity>) {
                    coroutineScope.launch(Dispatchers.IO) {
                        database.newsDao().let { newsDao ->
                            if (force) {
                                newsDao.deleteAll()
                            }
                            newsDao.insertAll(result)
                        }
                    }
                }

                override fun onFailure(throwable: Throwable?) {
                    TODO("Not yet implemented")
                }
            },
            pageSize = PAGE_SIZE,
            language = language,
            page = (if (force) 1 else database.newsDao().countNewsItems()
                .also { Timber.d("News (null category) count: $it") }.div(PAGE_SIZE) + 1).also {
                Timber.d("Request page: $it")
            }
        )
    }
}