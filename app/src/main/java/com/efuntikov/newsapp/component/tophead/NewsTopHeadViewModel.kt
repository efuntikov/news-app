package com.efuntikov.newsapp.component.tophead

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsTopHeadViewModel @Inject constructor() : BaseViewModel() {
//    val newsList = mutableStateOf<List<NewsItemEntity>>(emptyList())
//
//    fun load() {
//        if (observeEverythingJob != null) {
//            return
//        }
//
//        viewModelScope.launch(Dispatchers.Default) {
//            newsService.observeEverything().cancellable().collect { everythingNewsList ->
//                if (everythingNewsList.isEmpty()) {
//                    fetchNews()
//                }
//                newsList.value = everythingNewsList
//            }
//        }
//    }

//    fun stopObserving() {
//        observeEverythingJob?.cancel()
//        observeEverythingJob = null
//    }

//    suspend fun fetchNews() {
//        newsService./*getTopNews*/fetchEverything()
//    }
}
