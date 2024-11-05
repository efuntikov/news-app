package com.efuntikov.newsapp.component.details

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.efuntikov.newsapp.LOREM_TEXT
import com.efuntikov.newsapp.component.animation.loading.SlidingAnimationText
import com.efuntikov.newsapp.getViewModel
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsItemDetails(navController: NavController, itemId: Long) {
    val viewModel: NewsItemDetailsViewModel = getViewModel(key = itemId.toString())
    val imageUrl by viewModel.imageUrl
    val title by viewModel.title
    val newsTextContent by viewModel.content

    LaunchedEffect(key1 = "initial") {
        viewModel.setNewsItemId(itemId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = imageUrl,
                contentDescription = "news image"
            )

            Title(newsTitle = title)
        }

        Content(newsTextContent = newsTextContent)
    }
}

@Composable
private fun Title(newsTitle: String?) {
    Crossfade(targetState = newsTitle, label = "news_title_crossfade") { titlez ->
        when (titlez) {
            null -> SlidingAnimationText(textPlaceholderWidth = 200.dp)
            else -> Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                style = MaterialTheme.typography.titleLarge,
                text = titlez,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun Content(newsTextContent: String?) {
    Crossfade(targetState = newsTextContent, label = "news_content_crossfade") { content ->
        when (content) {
            null -> Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                SlidingAnimationText(textPlaceholderWidth = 200.dp)
                Spacer(modifier = Modifier.height(8.dp))
                SlidingAnimationText(textPlaceholderWidth = 250.dp)
                Spacer(modifier = Modifier.height(8.dp))
                SlidingAnimationText(textPlaceholderWidth = 140.dp)
                Spacer(modifier = Modifier.height(8.dp))
                SlidingAnimationText(textPlaceholderWidth = 180.dp)
            }
            else -> Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = content
            )
        }
    }
}

@Preview
@Composable
private fun TitlePreview() {
    NewsAppTheme {
        Title(newsTitle = "Long news title goes here blah blah blah")
    }
}

@Preview
@Composable
private fun ContentPreview() {
    NewsAppTheme {
        Content(newsTextContent = LOREM_TEXT)
    }
}

@Preview
@Composable
private fun EntireScreenPreview() {
    NewsAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.planetware.com%2Fpictures%2Ffrance-f.htm&psig=AOvVaw1cmBo5P-PPJxTrqqxRvrFX&ust=1730914970970000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCLCv4vHexYkDFQAAAAAdAAAAABAE",
                    contentDescription = "news image"
                )

                Title(newsTitle = "Long news title goes here blah blah blah")
            }

            Content(newsTextContent = LOREM_TEXT)
        }
    }
}
