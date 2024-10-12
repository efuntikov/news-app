package com.efuntikov.newsapp.component.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.news.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListItemViewModel @Inject constructor(
    private val newsService: NewsService
) : BaseViewModel() {
    private var newsItemId: Long = 0

    val newsItemModel = mutableStateOf<NewsItemEntity?>(null)

    fun setNewsItemId(newsItemId: Long) {
        this.newsItemId = newsItemId

        viewModelScope.launch(Dispatchers.Default) {
            newsService.observeNewsItemById(newsItemId = newsItemId).cancellable()
                .collect { newsItem ->
                    newsItemModel.value = newsItem
                }
        }
//        fetchData()
    }

//    fun fetchData() {
//        newsItemModel.imageUrl?.let { url ->
//
//        }
//    }
}
