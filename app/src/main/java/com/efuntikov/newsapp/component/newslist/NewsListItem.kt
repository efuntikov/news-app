package com.efuntikov.newsapp.component.newslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.efuntikov.newsapp.model.NewsItemModel

@Composable
fun NewsListItem(newsItem: NewsItemModel) {
    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        Column {
            Text(newsItem.title)
            Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())
            Text(newsItem.textContent)
        }
    }
}
