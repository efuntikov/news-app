package com.efuntikov.newsapp.component.feed

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsListItemViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : BaseViewModel() {
    private var newsItemId: Long? = null
    private var newsItemObserverJob: Job? = null

    val newsItemModel = mutableStateOf<NewsItemEntity?>(null)
    val isLoading = mutableStateOf(true)

    fun setNewsItemId(newsItemId: Long) {
        if (this.newsItemId == newsItemId) {
            return
        }

        newsItemObserverJob?.cancel()
        this.newsItemId = newsItemId
        newsItemObserverJob = viewModelScope.launch(Dispatchers.Default) {
            newsUseCase.observeNewsItem(newsItemId = newsItemId).cancellable()
                .collect { newsItem: NewsItemEntity? ->
                    Timber.i("Received item update: $newsItem")
                    newsItemModel.value = newsItem
                    isLoading.value = newsItem == null
                }
        }
    }
}
