package com.efuntikov.newsapp.component.feed

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun NewsItemDetails(navController: NavController, itemId: Long) {

}

@Preview
@Composable
fun NewsItemDetailsPreview() {
    NewsItemDetails(navController = rememberNavController(), itemId = 0)
}
