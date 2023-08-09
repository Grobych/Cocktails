package com.globa.cocktails.ui.util

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.cocktails.ui.R
import com.globa.cocktails.ui.theme.AppTheme

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {

    val infiniteTransition = rememberInfiniteTransition(label = "Shacker Animation")
    val degree = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 300,
                delayMillis = 0,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Shacker Animation"
    )

    Box(
        modifier = modifier
            .background(color = backgroundColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val id = if (isSystemInDarkTheme()) R.drawable.ic_shaker_dark else R.drawable.ic_shacker_light
        Image(
            modifier = Modifier
                .size(75.dp,150.dp)
                .rotate(degree.value)
            ,
            imageVector = ImageVector.vectorResource(id = id),
            contentDescription = stringResource(id = R.string.loading_string)
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoadingAnimationPreview() {
    AppTheme {
        Surface {
            LoadingAnimation()
        }
    }
}