package com.efuntikov.newsapp.component.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.efuntikov.newsapp.component.BaseViewModel
import com.efuntikov.newsapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsItemDetailsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
): BaseViewModel() {
    private var newsItemId: Long? = null

    val imageUrl = mutableStateOf<String?>(null)
    val title = mutableStateOf<String?>(null)
    val content = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(true)

    fun setNewsItemId(newsItemId: Long) {
        this.newsItemId = newsItemId

        viewModelScope.launch(Dispatchers.IO) {
            newsUseCase.observeNewsItem(newsItemId = newsItemId).cancellable().flowOn(Dispatchers.IO)
                .collect { newsItem ->
                    imageUrl.value = newsItem.imageUrl
                    title.value = newsItem.title
                    content.value = newsItem.textContent
                    isLoading.value = false
                }
        }
    }
}
