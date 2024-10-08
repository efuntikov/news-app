package com.efuntikov.newsapp.component.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.news.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsService: NewsService
) : BaseViewModel() {
    private var observeEverythingJob: Job? = null

    val newsList = mutableStateOf<List<NewsItemEntity>>(emptyList())
    val newsFeedRefreshing = mutableStateOf(false)

    fun load() {
        if (observeEverythingJob != null) {
            return
        }

        newsFeedRefreshing.value = true

        viewModelScope.launch(Dispatchers.Default) {
            newsService.observeEverything().cancellable().collect { everythingNewsList ->
                newsList.value = everythingNewsList
                if (everythingNewsList.isEmpty()) {
                    fetchNews()
                } else {
                    newsFeedRefreshing.value = false
                }
            }
        }
    }

//    fun stopObserving() {
//        observeEverythingJob?.cancel()
//        observeEverythingJob = null
//    }

    suspend fun fetchNews() {
        newsService./*getTopNews*/fetchEverything()
    }
}
