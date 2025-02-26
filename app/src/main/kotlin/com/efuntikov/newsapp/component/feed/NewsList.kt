package com.efuntikov.newsapp.component.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.efuntikov.newsapp.R
import com.efuntikov.newsapp.component.button.ScrollUpCircleButton
import com.efuntikov.newsapp.component.topbar.TopBar
import com.efuntikov.newsapp.component.topbar.TopBarState
import com.efuntikov.newsapp.component.tophead.NewsTopHeadSection
import com.efuntikov.newsapp.getViewModel
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterialApi::class, ExperimentalUuidApi::class)
@Composable
fun NewsListScreen(modifier: Modifier = Modifier, navController: NavController) {
    val newsListViewModel: NewsListViewModel = getViewModel()

    val newsList by newsListViewModel.newsList

    val refreshScope = rememberCoroutineScope()
    val refreshing by newsListViewModel.newsFeedRefreshing

    fun refresh() = refreshScope.launch {
        newsListViewModel.fetchNews(force = true)
    }

    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= newsList.size - 3) {
                    newsListViewModel.fetchMore()
                }
            }
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(key1 = "initial") {
        newsListViewModel.load()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val barState = remember {
            TopBarState(
                title = "News",
                leadingIcon = R.drawable.ic_settings,
                leadingIconClick = { navController.navigate("settings") })
        }
        TopBar(
            topBarState = barState
        )

        val isAtTop by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
            }
        }
        val scrollToTopAlpha by animateFloatAsState(
            targetValue = if (listState.isScrollInProgress) 1f else if (isAtTop) 0f else 0.4f,
            animationSpec = tween(durationMillis = 150), label = "scrollToTopButtonAlpha"
        )

        Box(modifier = Modifier.pullRefresh(state)) {
            Box(contentAlignment = Alignment.BottomEnd) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        NewsTopHeadSection(navController = navController)
                    }
                    item {
                        LatestNewsLabel()
                    }
                    if (newsList.isNotEmpty()) {
                        items(
                            items = newsList,
                            key = { it.id.let { id ->
                                if (id == -1L) Uuid.random().toHexString() else id
                            } },
                            itemContent = { newsItem ->
                                NewsListItem(modifier = Modifier.clickable {
                                    navController.navigate("details/${newsItem.id}")
                                }, newsItem.id)
                            }
                        )
                        item {
                            if (listState.isScrollInProgress) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
                if (!isAtTop) {
                    Box(modifier = Modifier.size(90.dp), contentAlignment = Alignment.Center) {
                        ScrollUpCircleButton(
                            onClick = { listState.animateScrollToItem(index = 0) },
                            buttonAlpha = scrollToTopAlpha,
                            size = 48.dp
                        )
                    }
                }
            }

            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
private fun LatestNewsLabel() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Latest news", style = MaterialTheme.typography.titleMedium)
        }
    }
}
