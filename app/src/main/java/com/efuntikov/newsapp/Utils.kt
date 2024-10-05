package com.efuntikov.newsapp

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.efuntikov.newsapp.component.BaseViewModel

@Composable
inline fun <reified VM : BaseViewModel> getViewModel(key: String? = null): VM =
    if (LocalContext.current is ContextWrapper) {
//        ((LocalContext.current as ContextWrapper).baseContext as ComponentActivity).let {
//            viewModel<VM>(viewModelStoreOwner = it, key = key)
//        }
        viewModel<VM>(key = key)
    } else {
        viewModel()
    }
