package com.efuntikov.newsapp.component.newslist

import androidx.compose.runtime.mutableStateOf
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.news.NewsCallback
import com.efuntikov.newsapp.domain.news.NewsService
import com.efuntikov.newsapp.model.NewsItemModel

class NewsListViewModel : BaseViewModel() {
    val newsList = mutableStateOf<List<NewsItemModel>>(emptyList())

    private val newsService = NewsService()

    fun fetchNews() {
        newsService./*getTopNews*/getEverything(object : NewsCallback {
            override fun onSuccess(result: List<NewsItemModel>) {
                newsList.value = result
            }
        })
    }
}
