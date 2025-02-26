package com.efuntikov.newsapp.component.animation.loading

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import com.efuntikov.newsapp.dpToPx
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

@Composable
fun BlurredLinearGradient(height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .graphicsLayer(alpha = 0.99f) // Ensures proper rendering for blur
            .blur(100.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded) // Blur radius
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                // Draw a linear gradient
                val gradient = Brush.linearGradient(
                    colors = listOf(Color(0x5F000000), Color(0xFF000000)),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height)
                )
                drawRect(brush = gradient)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BlurredLinearGradientPreview() {
    NewsAppTheme {
        BlurredLinearGradient(height = 300.dp)
    }
}