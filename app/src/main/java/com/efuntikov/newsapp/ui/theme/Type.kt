package com.efuntikov.newsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.efuntikov.newsapp.R

private val InkputAntiqua = FontFamily(
    Font(R.font.inknut_antiqua_light, FontWeight.Light),
    Font(R.font.inknut_antiqua_bold, FontWeight.Bold),
    Font(R.font.inknut_antiqua_extrabold, FontWeight.ExtraBold),
    Font(R.font.inknut_antiqua_regular, FontWeight.Normal),
    Font(R.font.inknut_antiqua_semibold, FontWeight.SemiBold)
)

val Typography = Typography().let {
    it.copy(
        headlineLarge = it.headlineLarge.copy(fontFamily = InkputAntiqua),
        headlineMedium = it.headlineMedium.copy(fontFamily = InkputAntiqua),
        headlineSmall = it.headlineSmall.copy(fontFamily = InkputAntiqua),
        bodyLarge = it.bodyLarge.copy(fontFamily = InkputAntiqua),
        bodyMedium = it.bodyMedium.copy(fontFamily = InkputAntiqua),
        bodySmall = it.bodySmall.copy(fontFamily = InkputAntiqua),
        titleLarge = it.titleLarge.copy(fontFamily = InkputAntiqua),
        labelSmall = it.labelSmall.copy(fontFamily = InkputAntiqua)
    )
}
