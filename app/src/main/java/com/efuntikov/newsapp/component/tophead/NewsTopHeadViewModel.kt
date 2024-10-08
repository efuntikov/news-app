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
    val categoriesList = mutableStateOf<Set<TopNewsCategory>>(emptySet())
    val selectedCategory = mutableStateOf(TopNewsCategory.BUSINESS)
//    val newsList = mutableStateOf<List<NewsItemEntity>>(emptyList())
//
    fun load() {
        categoriesList.value = TopNewsCategory.entries.toSet()
    }

    fun selectCategory(category: TopNewsCategory) {
        selectedCategory.value = category
    }

//    fun stopObserving() {
//        observeEverythingJob?.cancel()
//        observeEverythingJob = null
//    }

//    suspend fun fetchNews() {
//        newsService./*getTopNews*/fetchEverything()
//    }
}
