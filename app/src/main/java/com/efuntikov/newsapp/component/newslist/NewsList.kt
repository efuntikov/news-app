package com.efuntikov.newsapp.component.newslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListScreen(modifier: Modifier = Modifier) {
    val newsListViewModel: NewsListViewModel = getViewModel()

    val newsList by newsListViewModel.newsList

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableIntStateOf(15) }

    fun refresh() =
        refreshScope.launch {
            refreshing = true
            delay(1500)
            itemCount += 5

            newsListViewModel.fetchNews()

            refreshing = false
        }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(state)) {
        LazyColumn(Modifier.fillMaxSize()) {
            if (!refreshing) {
                itemsIndexed(
                    items = newsList,
                    key = { _, item -> item.getKey() },
                    contentType = { _, item -> item.getType() },
                    itemContent = { index, newsItem ->
                        NewsListItem(newsItem)
                    }
                )
//                item {
//                    Box(
//                        modifier = Modifier
//                            .height(30.dp)
//                            .fillParentMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        if (isFetchingMore) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(20.dp),
//                                color = OwnrTheme.colors.secondary1,
//                                strokeWidth = 2.dp
//                            )
//                        }
//                    }
//                }
            }
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListPreview() {
    NewsAppTheme {
        NewsListScreen()
    }
}
