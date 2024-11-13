package com.efuntikov.newsapp.component.tophead

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltViewModel
class NewsTopHeadViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : BaseViewModel() {

    companion object {
        private val emptyNewsItem = NewsItemEntity(
            id = -1, author = "", title = "", textContent = "",
            imageUrl = null,
            category = null
        )
    }

    private val loadingList =
        listOf(emptyNewsItem, emptyNewsItem, emptyNewsItem, emptyNewsItem)

    val categoriesList = mutableStateOf<Set<TopNewsCategory>>(emptySet())
    val selectedCategory = mutableStateOf(TopNewsCategory.BUSINESS)
    val newsListByCategory = mutableStateOf<List<NewsItemEntity>>(loadingList/*emptyList()*/)
    val isHorizontalScrollingEnabled = mutableStateOf(false)

    private var observeNewsCategoryJob: Job? = null

    fun load() {
        categoriesList.value = TopNewsCategory.entries.toSet()

        if (observeNewsCategoryJob != null) {
            return
        }

        observeNewsCategoryJob = observeNewsBySelectedCategory()
    }

    private fun observeNewsBySelectedCategory() = viewModelScope.launch(Dispatchers.IO) {
        newsUseCase.observeTopNewsByCategory(selectedCategory.value).cancellable().flowOn(Dispatchers.IO)
            .collect { categoryNewsList ->
                sleep(2000)
                newsListByCategory.value = categoryNewsList.ifEmpty { loadingList }
                if (categoryNewsList.isEmpty()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        newsUseCase.fetchTopNews(selectedCategory.value)
                    }
                } else {
//                    newsFeedRefreshing.value = false
                }
                isHorizontalScrollingEnabled.value = categoryNewsList.isNotEmpty()
            }
    }

    fun selectCategory(category: TopNewsCategory) {
        selectedCategory.value = category

        observeNewsCategoryJob?.cancel()
        observeNewsCategoryJob = observeNewsBySelectedCategory()
    }

//    fun stopObserving() {
//        observeEverythingJob?.cancel()
//        observeEverythingJob = null
//    }

//    suspend fun fetchNews() {
//        newsService./*getTopNews*/fetchEverything()
//    }
}
