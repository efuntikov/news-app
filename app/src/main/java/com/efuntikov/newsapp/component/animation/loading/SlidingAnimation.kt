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
    Color(0x5FFFFFFF),
    Color(0x1D646464),
    Color(0x5FFFFFFF),
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

private fun getTextFieldPath(textPlaceholderWidth: Float, curveShapeSize: Float) = Path().apply {
    moveTo(curveShapeSize, 0f)
    lineTo(curveShapeSize + textPlaceholderWidth, 0f)
    arcTo(
        rect = Rect(
            topLeft = Offset(curveShapeSize + textPlaceholderWidth, 0f),
            bottomRight = Offset(
                curveShapeSize + textPlaceholderWidth + curveShapeSize,
                curveShapeSize
            )
        ),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 180f,
        forceMoveTo = true
    )
    lineTo(curveShapeSize, curveShapeSize)
    arcTo(
        rect = Rect(
            topLeft = Offset(0f, 0f),
            bottomRight = Offset(curveShapeSize, curveShapeSize)
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 180f,
        forceMoveTo = false
    )
    close()
}

private fun getBoxPath(boxWidth: Float, boxHeight: Float) = Path().apply {
    moveTo(0f, 0f)
    lineTo(boxWidth, 0f)
    lineTo(boxWidth, boxHeight)
    lineTo(0f, boxHeight)
    close()
}

@Composable
fun SlidingAnimationText(textPlaceholderWidth: Dp, curveShapeSize: Dp = 16.dp) {
    val baseSize = dpToPx(textPlaceholderWidth)
    val arcSize = dpToPx(curveShapeSize)
    val textFieldPath = remember {
        getTextFieldPath(
            textPlaceholderWidth = baseSize,
            curveShapeSize = arcSize
        )
    }
    SlidingAnimation(
        shapePath = textFieldPath,
        width = textPlaceholderWidth + curveShapeSize * 2,
        height = curveShapeSize
    )
}

@Preview
@Composable
private fun SlidingAnimationTextPreview() {
    NewsAppTheme {
        SlidingAnimationText(textPlaceholderWidth = 200.dp)
    }
}

@Composable
fun SlidingAnimationBox(width: Dp, height: Dp) {
    val boxWidth = dpToPx(width)
    val boxHeight = dpToPx(height)
    val boxPath = remember {
        getBoxPath(
            boxWidth = boxWidth,
            boxHeight = boxHeight
        )
    }
    SlidingAnimation(
        shapePath = boxPath,
        width = width,
        height = height
    )
}

@Preview
@Composable
private fun SlidingAnimationBoxPreview() {
    NewsAppTheme {
        SlidingAnimationBox(width = 200.dp, height = 100.dp)
    }
}

@Composable
private fun SlidingAnimation(shapePath: Path, width: Dp, height: Dp) {
    Box {
        val limit = 1.2f
        val transition = rememberInfiniteTransition(label = "shimmer")
        val progressAnimated by transition.animateFloat(
            initialValue = -limit,
            targetValue = limit,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = "shimmer"
        )
        val color = MaterialTheme.colorScheme.secondary
        Box(
            modifier = Modifier
                .width(width)
                .height(height)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val offset = size.width * progressAnimated

                    val gradientBrush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(offset, size.height / 2f - 100),
                        end = Offset(offset + size.width, size.height / 2f + 100)
                    )

                    onDrawBehind {
                        // Destination
                        drawPath(
                            path = shapePath,
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
