package com.efuntikov.newsapp.usecase

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.repository.dao.NewsFeed
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.news.NewsService
import com.efuntikov.newsapp.domain.service.settings.SettingsService
import com.efuntikov.newsapp.domain.service.tophead.TopHeadNewsServiceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(
    private val settingsService: SettingsService,
    private val newsService: NewsService,
    private val topHeadNewsServiceImpl: TopHeadNewsServiceImpl
): NewsUseCase {
    override fun observeEverything(): NewsFeed {
        return newsService.observeEverything()
    }

    override fun observeNewsItem(newsItemId: Long): Flow<NewsItemEntity> {
        return newsService.observeNewsItemById(newsItemId = newsItemId)
    }

    override suspend fun fetchEverything(force: Boolean) {
        settingsService.getLanguage().let { lang ->
            newsService.fetchEverything(language = lang, force = force)
        }
    }

    override fun fetchTopNews() {
        topHeadNewsServiceImpl.fetchTopNews()
    }

    override fun fetchTopNews(category: TopNewsCategory) {
        settingsService.getLanguage().let { lang ->
            topHeadNewsServiceImpl.fetchTopNews(category = category, language = lang)
        }
    }

    override fun observeTopNewsByCategory(category: TopNewsCategory): NewsFeed {
        return topHeadNewsServiceImpl.observeTopNewsByCategory(category = category)
    }
}
