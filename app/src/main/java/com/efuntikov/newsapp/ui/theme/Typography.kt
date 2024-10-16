package com.efuntikov.newsapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.efuntikov.newsapp.R



data class NewsAppTypography(
    val h1: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        fontSize = 25.sp,
        color = Color.Black,
        letterSpacing = 0.5.sp
    ),
    val h2: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        fontSize = 21.sp,
        color = Color.Black,
        letterSpacing = 0.5.sp
    ),
    val mainHeader: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        color = Color.Black,
        fontSize = 18.sp
    ),
    val body: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        color = Color.Black,
        fontSize = 16.sp
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        color = Color.Black,
        fontSize = 14.sp
    ),
    val captionSmall: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        color = Color.Black,
        fontSize = 12.sp
    ),
    val captionExtraSmall: TextStyle = TextStyle(
        fontFamily = InkputAntiqua,
        color = Color.Black,
        fontSize = 11.sp
    )
)

//internal val LocalTypography = staticCompositionLocalOf { NewsAppTypography() }
