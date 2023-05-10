package com.globa.cocktails.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.globa.cocktails.R
import com.globa.cocktails.ui.theme.AppTheme
import com.globa.cocktails.ui.theme.DPs.circleButtonSize
import com.globa.cocktails.ui.theme.DPs.circleIconSize

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    buttonSize: Dp = circleButtonSize,
    iconSize: Dp = circleIconSize,
    onClickAction: () -> Unit,
) {
    IconButton(
        onClick = { onClickAction() },
        modifier = modifier
            .clip(CircleShape)
            .size(buttonSize)
            .background(color = buttonColor)

    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.size(size = iconSize)
        )
    }
}

@Composable
fun AddButton(
    onClickAction: () -> Unit
) {
    CircleButton(
        iconId = R.drawable.ic_add,
        onClickAction = { onClickAction() }
    )
}

@Composable
fun MenuButton(
    onClickAction: () -> Unit
) {
    CircleButton(
        iconId = R.drawable.ic_menu,
        onClickAction = onClickAction,
        buttonColor = MaterialTheme.colorScheme.surfaceVariant,
        iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        iconSize = 19.dp
    )
}

@Composable
fun FavoriteButton(
    onClickAction: () -> Unit,
    isFavorited: Boolean = false
) {
    CircleButton(
        iconId = if (isFavorited) R.drawable.ic_favorite_en else R.drawable.ic_favorite_dis,
        onClickAction = onClickAction
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun AddButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            AddButton {}
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun MenuButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            MenuButton {}
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun NotFavoriteButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            FavoriteButton(onClickAction = {})
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun FavoriteButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            FavoriteButton(isFavorited = true, onClickAction = {})
        }
    }
}