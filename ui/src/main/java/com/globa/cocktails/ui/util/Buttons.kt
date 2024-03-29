package com.globa.cocktails.ui.util

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.cocktails.ui.R
import com.globa.cocktails.ui.theme.AppTheme
import com.globa.cocktails.ui.theme.DPs.iconButtonSize

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    iconColor: Color = MaterialTheme.colorScheme.primary,
) {
    IconButton(
        onClick = {onClickAction()},
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    IconButton(
        onClick = {onClickAction()},
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.size(iconButtonSize)
        )
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    isFavorited: Boolean,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    val vectorResource = if (isFavorited) R.drawable.ic_favorite_en else R.drawable.ic_favorite_dis
    Box(
        modifier = modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = vectorResource),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.size(24.dp).clickable { onClickAction() }
        )
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    iconColor: Color = MaterialTheme.colorScheme.onSurface
) {
    IconButton(
        onClick = {onClickAction()},
        modifier = modifier.size(20.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.size(iconButtonSize)
        )
    }
}

//TODO: rewrite buttons
@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    iconColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier.size(24.dp).clickable { onClickAction() }
        )
    }
}

@Composable
fun FooterButton(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit,
    icon: Int,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    text: String,
    textColor: Color = iconColor
) {
    Column(
        modifier = modifier
            .requiredWidth(68.dp)
            .clickable { onClickAction() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector
                .vectorResource(id = icon),
            contentDescription = "",
            tint = iconColor
        )
        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.labelSmall.plus(
                TextStyle(color = textColor)
            )
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
fun AddButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            AddButton(onClickAction = {})
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
fun MenuButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            MenuButton(onClickAction = {})
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
fun NotFavoriteButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            FavoriteButton(isFavorited = false, onClickAction = {})
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
fun FavoriteButtonPreview() {
    AppTheme {
        Surface(Modifier.size(45.dp)) {
            FavoriteButton(isFavorited = true, onClickAction = {})
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
fun FooterButtonPreview() {
    AppTheme {
        Surface {
            FooterButton(onClickAction = { }, icon = R.drawable.ic_random_receipe, text = "Test text")
        }
    }
}