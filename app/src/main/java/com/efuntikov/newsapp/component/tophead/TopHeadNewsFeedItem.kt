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
import com.efuntikov.newsapp.component.animation.loading.SlidingAnimationText
import com.efuntikov.newsapp.component.feed.NewsListItemViewModel
import com.efuntikov.newsapp.dpToPx
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

val newsTopHeadItemShape = RoundedCornerShape(
    topStart = CornerSize(16.dp),
    topEnd = CornerSize(16.dp),
    bottomStart = CornerSize(16.dp), bottomEnd = CornerSize(16.dp)
)

@Composable
fun TopHeadNewsFeedItem(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)

    val newsItemModel by viewModel.newsItemModel
    newsItemModel?.let { newsItem ->
        Column(
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.minus(dpToPx(16.dp)).dp)
                .wrapContentHeight()
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

@Composable
private fun BottomSection(newsItemId: Long) {
    val viewModel: NewsListItemViewModel = getViewModel(key = newsItemId.toString())
    viewModel.setNewsItemId(newsItemId)
    val newsItemModel by viewModel.newsItemModel

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        NewsTitleSection(title = newsItemModel?.title)
        Spacer(modifier = Modifier.height(8.dp))
        AuthorSection(author = newsItemModel?.author)
    }
}

@Composable
fun NewsTitleSection(title: String?) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        title?.let { title ->
            Text(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surface),
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                text = title
            )
        } ?: run {
            SlidingAnimationText(textPlaceholderWidth = 300.dp)
        }
    }
}

@Preview
@Composable
fun NewsTitleSectionPreview() {
    NewsAppTheme {
        NewsTitleSection(title = "Top head news title very long")
    }
}

@Composable
fun AuthorSection(author: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            modifier = Modifier.size(36.dp),
            contentDescription = "top head news item author icon"
        )
        Spacer(modifier = Modifier.width(8.dp))
        author?.let { author ->
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 10.sp,
                text = author
            )
        } ?: run {
            SlidingAnimationText(textPlaceholderWidth = 200.dp)
        }
    }
}

@Preview
@Composable
fun AuthorSectionPreview() {
    NewsAppTheme {
        AuthorSection(author = "John Doe")
    }
}
