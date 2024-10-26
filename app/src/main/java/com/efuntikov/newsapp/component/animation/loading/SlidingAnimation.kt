package com.efuntikov.newsapp.component.animation.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.efuntikov.newsapp.R
import com.efuntikov.newsapp.dpToPx
import com.efuntikov.newsapp.ui.theme.NewsAppTheme

private val gradientColors = listOf(
    Color(0xFFFFFFFF),
    Color(0x1D646464),
    Color(0xFFFFFFFF),
)

@Preview
@Composable
private fun SingleGradientTest() {

    Column(
        Modifier.padding(20.dp)
    ) {
        val painterStar = painterResource(id = R.drawable.ic_settings)

        val limit = 1.0f

        val transition = rememberInfiniteTransition(label = "shimmer")
        val progressAnimated by transition.animateFloat(
            initialValue = -limit,
            targetValue = limit,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "shimmer"
        )

        Text(text = "Animated progress: $progressAnimated")
        Box(
            modifier = Modifier
                .size(500.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val width = size.width
                    val height = size.height

                    val offset = width * progressAnimated

                    val gradientBrush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(offset, height / 2f - 100),
                        end = Offset(offset + width, height / 2f + 100)
                    )

                    onDrawBehind {
                        // Destination
                        with(painterStar) {
                            draw(
                                size = Size(width, width)
                            )
                        }

                        // Source
                        drawRect(
                            brush = gradientBrush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )
    }
}

@Composable
fun SlidingAnimationText(textPlaceholderWidth: Dp) {
    Box {
        val limit = 1.0f
        val transition = rememberInfiniteTransition(label = "shimmer")
        val progressAnimated by transition.animateFloat(
            initialValue = -limit,
            targetValue = limit,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "shimmer"
        )
        val baseSize = dpToPx(textPlaceholderWidth)
        val textFieldPath = remember {
            Path().apply {
                moveTo(baseSize * 0.25f, 0f)
                lineTo(baseSize * 0.75f, 0f)
                arcTo(
                    rect = Rect(
                        topLeft = Offset(baseSize * 0.5f, 0f),
                        bottomRight = Offset(baseSize, baseSize * 0.5f)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = true
                )
                lineTo(baseSize * 0.25f, baseSize * 0.5f)
                arcTo(
                    rect = Rect(
                        topLeft = Offset(0f, 0f),
                        bottomRight = Offset(baseSize * 0.5f, baseSize * 0.5f)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )
                close()
            }
        }
        val color = MaterialTheme.colorScheme.secondary
        Box(
            modifier = Modifier
                .width(textPlaceholderWidth)
                .height(textPlaceholderWidth * 0.5f)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val width = size.width
                    val height = size.height

                    val offset = width * progressAnimated

                    val gradientBrush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(offset, height / 2f - 100),
                        end = Offset(offset + width, height / 2f + 100)
                    )

                    onDrawBehind {
                        // Destination
                        drawPath(
                            path = textFieldPath,
                            color = color,
                            style = Fill
                        )

                        // Source
                        drawRect(
                            brush = gradientBrush,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                }
        )
    }
}

@Preview
@Composable
fun SlidingAnimationTextPreview() {
    NewsAppTheme {
        SlidingAnimationText(textPlaceholderWidth = 200.dp)
    }
}
