package com.efuntikov.newsapp.component.tophead

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.efuntikov.newsapp.getViewModel

@Composable
fun NewsTopHeadSection() {
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            CategoriesRow()
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalFeed()
        }
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
            Spacer(modifier = Modifier.width(2.dp))
        }
        Text(
            text = category.name.lowercase(),
            fontStyle = FontStyle.Normal,
            fontWeight = if (category == selectedCategory) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun HorizontalFeed() {
    val viewModel = getViewModel<NewsTopHeadViewModel>()
    val newsFeedByCategory by viewModel.newsListByCategory
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = newsFeedByCategory,
            key = { _, item -> item.getKey() },
            contentType = { _, item -> item.getType() },
            itemContent = { _, newsItem ->
                TopHeadNewsFeedItem(newsItemId = newsItem.id)
            }
        )
    }
}

@Preview
@Composable
fun NewsTopHeadSectionPreview() {
    NewsTopHeadSection()
}

@Preview
@Composable
fun CategoryPreview() {
    Category(category = TopNewsCategory.ENTERTAINMENT)
}
