package com.efuntikov.newsapp

import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.efuntikov.newsapp.component.BaseViewModel

@Composable
inline fun <reified VM : BaseViewModel> getViewModel(key: String? = null): VM =
    if (LocalContext.current is ContextWrapper) {
//        ((LocalContext.current as ContextWrapper).baseContext as ComponentActivity).let {
//            viewModel<VM>(viewModelStoreOwner = it, key = key)
//        }
        hiltViewModel<VM>(key = key)
    } else {
        hiltViewModel()
    }
