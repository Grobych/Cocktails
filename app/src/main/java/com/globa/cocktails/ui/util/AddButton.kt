package com.globa.cocktails.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.globa.cocktails.R
import com.globa.cocktails.ui.theme.AppTheme

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    contentDescription: String = "Add cocktail",
    onClickAction: () -> Unit
) {
    IconButton(
        onClick = { onClickAction() },
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
//            .size(45.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddButtonPreview() {
    AppTheme {
        Surface {
            AddButton {}
        }
    }
}