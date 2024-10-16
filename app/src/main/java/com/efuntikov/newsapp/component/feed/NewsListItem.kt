package com.efuntikov.newsapp.component.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.efuntikov.newsapp.R
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsListItem(modifier: Modifier, newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)
    val newsItemModel by viewModel.newsItemModel

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.DarkGray)
    ) {
        newsItemModel?.let { newsItem ->
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
}

@Preview
@Composable
fun NewsListItemPreview() {
    NewsListItem(
        modifier = Modifier,
        newsItemId = 123
    )
}
