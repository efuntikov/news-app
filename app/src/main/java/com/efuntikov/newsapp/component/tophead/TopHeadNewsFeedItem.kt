package com.efuntikov.newsapp.component.tophead

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.efuntikov.newsapp.R
import com.efuntikov.newsapp.component.feed.NewsListItemViewModel
import com.efuntikov.newsapp.getViewModel

val newsTopHeadItemShape = RoundedCornerShape(
    topStart = CornerSize(16.dp),
    topEnd = CornerSize(16.dp),
    bottomStart = CornerSize(16.dp), bottomEnd = CornerSize(3.dp)
)

@Composable
fun TopHeadNewsFeedItem(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)

    val newsItemModel by viewModel.newsItemModel
    newsItemModel?.let { newsItem ->
        Column(
            modifier = Modifier
                .width(350.dp)
                .height(300.dp)
                .background(color = MaterialTheme.colorScheme.surface, shape = newsTopHeadItemShape)
                .clip(newsTopHeadItemShape)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                model = newsItem.imageUrl,
                contentDescription = "top head news item image"
            )
            BottomSection(newsItemId = newsItemId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSection(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)
    val newsItemModel by viewModel.newsItemModel

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        newsItemModel?.let { newsItem ->
            Text(
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                text = newsItem.title
            )

            newsItem.author?.let { author ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_avatar),
                        tint = null,
                        contentDescription = "top head news item author icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.background(Color.Red),
                        style = MaterialTheme.typography.bodySmall,
                        text = author
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TopHeadNewsFeedItemPreview() {
    TopHeadNewsFeedItem(newsItemId = 123)
}