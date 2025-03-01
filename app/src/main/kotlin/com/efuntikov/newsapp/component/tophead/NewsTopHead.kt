package com.efuntikov.newsapp.component.tophead

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun NewsTopHeadSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            CategoriesRow()
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalFeed(navController = navController)
    }
}

@Composable
fun CategoriesRow() {
    val viewModel = getViewModel<NewsTopHeadViewModel>()
    val categories by viewModel.categoriesList
    LaunchedEffect(key1 = "initial") {
        viewModel.load()
    }
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEach { category ->
            Category(category = category)
        }
    }
}

@Composable
fun CategoryIndicator() {
    Box(
        modifier = Modifier
            .size(5.dp)
            .clip(shape = CircleShape)
            .background(color = Color.Red, shape = CircleShape)
    )
}

@Composable
fun Category(category: TopNewsCategory) {
    val viewModel = getViewModel<NewsTopHeadViewModel>()
    val selectedCategory by viewModel.selectedCategory
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { viewModel.selectCategory(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(visible = category == selectedCategory) {
            CategoryIndicator()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = category.name.lowercase(),
            fontStyle = FontStyle.Normal,
            fontWeight = if (category == selectedCategory) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HorizontalFeed(navController: NavController) {
    val viewModel = getViewModel<NewsTopHeadViewModel>()
    val newsFeedByCategory by viewModel.newsListByCategory
    val isHorizontalScrollingEnabled by viewModel.isHorizontalScrollingEnabled

    val pagerState = rememberPagerState(pageCount = { newsFeedByCategory.size })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 8.dp),
        userScrollEnabled = isHorizontalScrollingEnabled,
        beyondViewportPageCount = 2,
        key = {
            newsFeedByCategory[it].id.let { id ->
                if (id == -1L) Uuid.random().toHexString() else id
            }
        },
    ) { page ->
        TopHeadNewsFeedItem(modifier = Modifier.clickable {
            navController.navigate("details/${newsFeedByCategory[page].id}")
        }, newsItemId = newsFeedByCategory[page].id)
    }
}

@Preview
@Composable
fun CategoryPreview() {
    NewsAppTheme {
        Category(category = TopNewsCategory.ENTERTAINMENT)
    }
}
