package com.efuntikov.newsapp.component.feed

import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.service.news.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListItemViewModel @Inject constructor(
    private val newsService: NewsService
) : BaseViewModel() {
    private var newsItemId: Long = 0

    fun setNewsItemId(newsItemId: Long) {
        this.newsItemId = newsItemId


        fetchData()
    }

    fun fetchData() {
//        newsItemModel.imageUrl?.let { url ->
//
//        }
    }
}
