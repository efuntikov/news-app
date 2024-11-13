package com.efuntikov.newsapp.component.button

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.efuntikov.newsapp.dpToPx
import com.efuntikov.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.launch

@Composable
fun ScrollUpCircleButton(
    buttonAlpha: Float,
    size: Dp = 60.dp,
    onClick: (suspend () -> Unit)? = null
) {
    val sizePx = dpToPx(size)
    val surfaceColor = MaterialTheme.colorScheme.tertiary
    val onSurfaceColor = MaterialTheme.colorScheme.onTertiary
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color = Color.Transparent)
            .clickable(
                onClick = {
                    coroutineScope.launch {
                        onClick?.invoke()
                    }
                },
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            )
            .graphicsLayer {
                shape = CircleShape
                alpha = buttonAlpha
                compositingStrategy = CompositingStrategy.Auto
            }
            .drawWithCache {
                onDrawBehind {
                    drawCircle(color = surfaceColor)
                    drawLine(
                        onSurfaceColor,
                        start = Offset(sizePx / 2, sizePx / 3),
                        end = Offset(sizePx / 4, sizePx / 1.6f),
                        cap = StrokeCap.Round,
                        strokeWidth = 10f
                    )
                    drawLine(
                        onSurfaceColor,
                        start = Offset(sizePx / 2, sizePx / 3),
                        end = Offset(3 * sizePx / 4, sizePx / 1.6f),
                        cap = StrokeCap.Round,
                        strokeWidth = 10f
                    )
                }
            }
    )
}

@Preview
@Composable
private fun ScrollUpCircleButtonPreview() {
    NewsAppTheme {
        val alphaAnimated by rememberInfiniteTransition(label = "shimmer").animateFloat(
            initialValue = 0.5f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "shimmer"
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ScrollUpCircleButton(buttonAlpha = alphaAnimated)
        }
    }
}