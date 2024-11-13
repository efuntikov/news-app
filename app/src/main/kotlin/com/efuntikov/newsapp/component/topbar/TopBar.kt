package com.efuntikov.newsapp.component.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

data class TopBarState(
    val title: String,
    @DrawableRes val leadingIcon: Int? = null,
    val leadingIconClick: () -> Unit = {},
    @DrawableRes val trailingIcon: Int? = null,
    val trailingIconClick: () -> Unit = {}
)

@Composable
fun TopBar(topBarState: TopBarState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.Center) {
            topBarState.leadingIcon?.let { leadingIconRes ->
                IconButton(onClick = {
                    topBarState.leadingIconClick()
                }) {
                    Icon(
                        painter = painterResource(id = leadingIconRes),
                        contentDescription = "top bar leading icon",
                    )
                }
            }
        }
        Text(
            text = topBarState.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.size(64.dp)) {
            topBarState.trailingIcon?.let { trailingIconRes ->
                IconButton(onClick = {
                    topBarState.trailingIconClick()
                }) {
                    Icon(
                        painter = painterResource(id = trailingIconRes),
                        contentDescription = "top bar trailing icon",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    NewsAppTheme {
        TopBar(topBarState = TopBarState(title = "News"))
    }
}
