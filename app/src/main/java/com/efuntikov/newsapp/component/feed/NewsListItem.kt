package com.efuntikov.newsapp.component.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.efuntikov.newsapp.component.tophead.newsTopHeadItemShape
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity
import com.efuntikov.newsapp.getViewModel

@Composable
fun NewsListItem(modifier: Modifier, newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)
    val newsItemModel by viewModel.newsItemModel

    NewsListItemContent(modifier, newsItemModel)
}

@Composable
private fun NewsListItemContent(modifier: Modifier, newsItemModel: NewsItemEntity?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Spacer(Modifier.width(12.dp))
        Box(
            modifier = modifier
                .weight(1f)
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = newsTopHeadItemShape
                )
                .clip(newsTopHeadItemShape)
        ) {
            newsItemModel?.let { newsItem ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Crop,
                        model = newsItem.imageUrl,
                        contentDescription = "news image"
                    )
                    Spacer(
                        modifier = Modifier
                            .width(12.dp)
                    )
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            color = Color.Cyan,
                            text = newsItem.title,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
        Spacer(Modifier.width(12.dp))
    }
}

@Preview
@Composable
fun NewsListItemContentPreview() {
    NewsListItemContent(
        Modifier,
        newsItemModel = NewsItemEntity(
            title = "News title",
            imageUrl = null,
            author = "Author",
            textContent = "News content",
            category = null
        )
    )
}
