package com.efuntikov.newsapp

import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
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


@Composable
fun dpToPx(dp: Dp): Float = with(LocalDensity.current) { dp.toPx() }

const val LOREM_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
        "Donec dolor ex, pharetra et vestibulum sit amet, sagittis vel mauris. " +
        "Duis malesuada elit in justo iaculis, at ultrices purus cursus. Mauris laoreet, nunc egestas aliquet volutpat, " +
        "mi arcu dignissim enim, volutpat feugiat nisi nibh ac massa. In interdum purus nec felis cursus, a vulputate purus " +
        "elementum. Nunc a urna at purus interdum aliquam sed a est. Phasellus dapibus leo at ligula vehicula mattis. Proin orci augue, " +
        "interdum ut massa viverra, scelerisque lacinia nulla. Morbi at gravida purus. In quis augue sem. " +
        "Nullam viverra, metus vitae pharetra ullamcorper, nisi sem dictum turpis, at elementum est nisl sit amet dolor. " +
        "Ut leo elit, interdum a fringilla vel, condimentum a sapien. Cras ante orci, " +
        "aliquet egestas augue vitae, placerat faucibus massa."
