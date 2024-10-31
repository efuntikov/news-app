package com.efuntikov.newsapp.component.tophead

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.efuntikov.newsapp.R
import com.efuntikov.newsapp.component.animation.loading.SlidingAnimationBox
import com.efuntikov.newsapp.component.animation.loading.SlidingAnimationText
import com.efuntikov.newsapp.component.feed.NewsListItemViewModel
import com.efuntikov.newsapp.dpToPx
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

val newsTopHeadItemShape = CornerSize(16.dp).let { cornerSize ->
    RoundedCornerShape(
        topStart = cornerSize,
        topEnd = cornerSize,
        bottomStart = cornerSize,
        bottomEnd = cornerSize
    )
}

@Composable
fun TopHeadNewsFeedItem(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)

    val newsItemModel by viewModel.newsItemModel
    val isLoading by viewModel.isLoading
    val housingWidth = LocalConfiguration.current.screenWidthDp.minus(dpToPx(16.dp)).dp
    newsItemModel?.let { newsItem ->
        Column(
            modifier = Modifier
                .width(housingWidth)
                .wrapContentHeight()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = newsTopHeadItemShape
                )
                .clip(newsTopHeadItemShape)
        ) {
            if (isLoading) {
                SlidingAnimationBox(width = housingWidth, height = 200.dp)
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                    model = newsItem.imageUrl,
                    contentDescription = "top head news item image"
                )
            }
            BottomSection(newsItemId = newsItemId)
        }
    }
}

@Composable
private fun BottomSection(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)
    val newsItemModel by viewModel.newsItemModel
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        NewsTitleSection(title = newsItemModel?.title, isLoading = isLoading)
        Spacer(modifier = Modifier.height(8.dp))
        AuthorSection(author = newsItemModel?.author, isLoading = isLoading)
    }
}

@Composable
fun NewsTitleSection(title: String?, isLoading: Boolean) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        if (isLoading) {
            SlidingAnimationText(textPlaceholderWidth = 250.dp)
        } else {
            title?.let {
                Text(
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    text = it
                )
            }
        }
    }
}

@Preview
@Composable
fun NewsTitleSectionPreview() {
    NewsAppTheme {
        NewsTitleSection(title = "Top head news title very long", isLoading = false)
    }
}

@Composable
fun AuthorSection(author: String?, isLoading: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            SlidingAnimationBox(width = 36.dp, height = 36.dp)
        } else {
            Image(
                painter = painterResource(R.drawable.ic_avatar),
                modifier = Modifier.size(36.dp),
                contentDescription = "top head news item author icon"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        if (isLoading) {
            SlidingAnimationText(textPlaceholderWidth = 200.dp)
        } else {
            author?.let { author ->
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 10.sp,
                    text = author
                )
            }
        }
    }
}

@Preview
@Composable
fun AuthorSectionPreview() {
    NewsAppTheme {
        AuthorSection(author = "John Doe", isLoading = false)
    }
}
