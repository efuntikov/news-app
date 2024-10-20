package com.efuntikov.newsapp.component.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.domain.service.settings.SettingsService
import com.efuntikov.newsapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val settingsService: SettingsService
) : BaseViewModel() {

    val newsList = mutableStateOf<List<NewsItemEntity>>(emptyList())
    val newsFeedRefreshing = mutableStateOf(false)

    fun load() {
        newsFeedRefreshing.value = true

        viewModelScope.launch(Dispatchers.Default) {
            newsUseCase.observeEverything().cancellable().collect { everythingNewsList ->
                newsList.value = everythingNewsList
                if (everythingNewsList.isEmpty()) {
                    newsUseCase.fetchEverything()
                } else {
                    newsFeedRefreshing.value = false
                }
            }

            settingsService.observeLanguage().cancellable().collect {
                fetchNews()
            }
        }
    }

    suspend fun fetchNews() {
        newsFeedRefreshing.value = true
        newsUseCase.fetchEverything()
    }
}
