package com.efuntikov.newsapp.component.tophead

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.efuntikov.newsapp.component.feed.NewsListItemViewModel
import com.efuntikov.newsapp.getViewModel

@Composable
fun TopHeadNewsFeedItem(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)

    val newsItemModel by viewModel.newsItemModel
    newsItemModel?.let { newsItem ->
        Column(modifier = Modifier
            .width(120.dp)
            .height(150.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 120.dp),
                model = newsItem.imageUrl,
                contentDescription = "news image"
            )
            Text(color = Color.Cyan, text = newsItem.title, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
fun TopHeadNewsFeedItemPreview() {
    TopHeadNewsFeedItem(newsItemId = 123)
}