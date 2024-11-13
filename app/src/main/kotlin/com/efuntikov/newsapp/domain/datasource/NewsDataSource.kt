package com.efuntikov.newsapp.domain.datasource

import com.efuntikov.newsapp.component.tophead.TopNewsCategory
import com.efuntikov.newsapp.domain.service.news.NewsCallback

interface NewsDataSource {
    fun getTopHeadlines(
        newsCallback: NewsCallback,
        category: TopNewsCategory,
        pageSize: Int,
        language: Language = Language.EN
    )

    fun getEverything(
        newsCallback: NewsCallback, pageSize: Int, page: Int,
        language: Language = Language.EN
    )
}
