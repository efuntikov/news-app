package com.efuntikov.newsapp.component.newslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.domain.repository.entity.NewsItemEntity

@Composable
fun NewsListItem(modifier: Modifier, newsItem: NewsItemEntity) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItem.getKey().toString())
    viewModel.setNewsItemId(newsItem.getKey())
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.DarkGray)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 120.dp),
                model = newsItem.imageUrl,
                contentDescription = "news image"
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )
            Text(color = Color.Cyan, text = newsItem.title)
//            Spacer(modifier = Modifier
//                .height(8.dp)
//                .fillMaxWidth())
//            Text(color = Color.Magenta, text = newsItem.textContent)
        }
    }
}

@Preview
@Composable
fun NewsListItemPreview() {
    NewsListItem(
        modifier = Modifier,
        newsItem = NewsItemEntity(
            title = "title",
            textContent = "content",
            author = "author",
            imageUrl = "https://i.insider.com/66e231c1cfb7f307e570bc7c?width=1200&format=jpeg"
        )
    )
}
