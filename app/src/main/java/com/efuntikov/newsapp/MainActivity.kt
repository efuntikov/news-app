package com.efuntikov.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.efuntikov.newsapp.component.feed.NewsListScreen
import com.efuntikov.newsapp.component.feed.NewsItemDetails
import com.efuntikov.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@Composable
private fun Content() {
    NewsAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()

            NavHost(navController, startDestination = "list") {
                composable("list") {
                    Crossfade(targetState = "news_list", label = "list_crossfade") {
                        NewsListScreen(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController
                        )
                    }
                }
                composable("details/{itemId}") { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString("itemId")
                    Crossfade(targetState = "news_item_details", label = "list_item_crossfade") {
                        if (itemId != null) {
                            NewsItemDetails(navController = navController, itemId = itemId.toLong())
                        }
                    }
                }
            }
        }
    }

}
