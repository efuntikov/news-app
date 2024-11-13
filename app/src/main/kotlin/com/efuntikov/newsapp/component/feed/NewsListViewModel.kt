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

    val newsList = mutableStateOf<List<NewsItemEntity>>(emptyList())
    val newsFeedRefreshing = mutableStateOf(false)

    private var loadNewsJob: Job? = null

    fun load() {
        if (loadNewsJob != null) {
            return
        }

        newsFeedRefreshing.value = true

        loadNewsJob = viewModelScope.launch(Dispatchers.Default) {
            newsUseCase.observeEverything().cancellable().collect { everythingNewsList ->
                Timber.i("Received news list(${everythingNewsList.size})")
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
