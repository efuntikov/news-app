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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsTopHeadViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : BaseViewModel() {

    companion object {
        private val fakeNewsItem = NewsItemEntity(
            id = -1, author = "fake", title = "fake", textContent = "fake",
            imageUrl = null,
            category = null
        )
    }

    private val loadingList =
        listOf(fakeNewsItem, fakeNewsItem, fakeNewsItem, fakeNewsItem)

    val categoriesList = mutableStateOf<Set<TopNewsCategory>>(emptySet())
    val selectedCategory = mutableStateOf(TopNewsCategory.BUSINESS)
    val newsListByCategory = mutableStateOf<List<NewsItemEntity>>(/*loadingList*/emptyList())

    private var observeNewsCategoryJob: Job? = null

    fun load() {
        categoriesList.value = TopNewsCategory.entries.toSet()

        if (observeNewsCategoryJob != null) {
            return
        }

        observeNewsCategoryJob = observeNewsBySelectedCategory()
    }

    private fun observeNewsBySelectedCategory() = viewModelScope.launch(Dispatchers.Default) {
        newsUseCase.observeTopNewsByCategory(selectedCategory.value).cancellable()
            .collect { categoryNewsList ->
                newsListByCategory.value = categoryNewsList.ifEmpty { loadingList }
                if (categoryNewsList.isEmpty()) {
                    newsUseCase.fetchTopNews(selectedCategory.value)
                } else {
//                    newsFeedRefreshing.value = false
                }
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
