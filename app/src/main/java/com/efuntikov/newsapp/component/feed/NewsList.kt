package com.efuntikov.newsapp.component.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.efuntikov.newsapp.component.tophead.NewsTopHeadSection
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListScreen(modifier: Modifier = Modifier, navController: NavController) {
    val newsListViewModel: NewsListViewModel = getViewModel()

    val newsList by newsListViewModel.newsList

    val refreshScope = rememberCoroutineScope()
    val refreshing by newsListViewModel.newsFeedRefreshing

    fun refresh() = refreshScope.launch {
        newsListViewModel.fetchNews()
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(key1 = "initial") {
        newsListViewModel.load()
    }

    Column(
        Modifier
            .pullRefresh(state)
            .background(color = MaterialTheme.colorScheme.background)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                NewsTopHeadSection()
            }
            itemsIndexed(
                items = newsList,
                key = { _, item -> item.getKey() },
                contentType = { _, item -> item.getType() },
                itemContent = { _, newsItem ->
                    NewsListItem(modifier = Modifier.clickable {
                        navController.navigate("details/${newsItem.id}")
                    }, newsItem.getKey())
                }
            )
        }

        PullRefreshIndicator(
            refreshing,
            state, /*Modifier.align(alignment = androidx.compose.ui.Alignment.Horizontal)*/
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListPreview() {
    NewsAppTheme {
        NewsListScreen(navController = rememberNavController())
    }
}
