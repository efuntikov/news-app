package com.efuntikov.newsapp.domain.service.datasource

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.service.news.NewsCallback

interface NewsDataSource {
    fun getTopHeadlines(
        newsCallback: NewsCallback,
        category: TopNewsCategory,
        pageSize: Int,
        language: Language = Language.EN
    )

    fun getEverything(newsCallback: NewsCallback)
}