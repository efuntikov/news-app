package com.efuntikov.newsapp.component.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.settings.SettingsService
import com.efuntikov.newsapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val settingsService: SettingsService
) : BaseViewModel() {

    companion object {
        private val emptyNewsItem = NewsItemEntity(
            id = -1, author = "", title = "", textContent = "",
            imageUrl = null,
            category = null
        )
    }

    private val loadingList =
        listOf(emptyNewsItem, emptyNewsItem, emptyNewsItem, emptyNewsItem, emptyNewsItem, emptyNewsItem)

    val newsList = mutableStateOf<List<NewsItemEntity>>(/*emptyList()*/loadingList)
    val newsFeedRefreshing = mutableStateOf(false)

    private var loadNewsJob: Job? = null

    fun load() {
        if (loadNewsJob != null) {
            return
        }

        newsFeedRefreshing.value = true

        loadNewsJob = viewModelScope.launch(Dispatchers.IO) {
            newsUseCase.observeEverything().cancellable().flowOn(Dispatchers.IO).collect { everythingNewsList ->
                Timber.i("Received news list(${everythingNewsList.size})")
                sleep(2000)
                newsList.value = everythingNewsList
                if (everythingNewsList.isEmpty()) {
                    newsUseCase.fetchEverything()
                } else {
                    newsFeedRefreshing.value = false
                }
            }

            settingsService.observeLanguage().cancellable().collect {
                fetchNews(force = true)
            }
        }
    }

    suspend fun fetchMore() {
        Timber.i("Fetching more news")
        fetchNews(force = false)
    }

    suspend fun fetchNews(force: Boolean = false) {
        newsFeedRefreshing.value = true
        withContext(Dispatchers.IO) {
            newsUseCase.fetchEverything(force)
        }
    }
}
