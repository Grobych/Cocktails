package com.globa.cocktails.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TagButton(
    text: String,
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit
) {
        Button(
            onClick = onClickAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = modifier
                .height(24.dp),
            shape = MaterialTheme.shapes.extraLarge,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TagButtonPreviewLight() {
    com.globa.cocktails.ui.theme.AppTheme {
        Surface {
            TagButton(text = "SomeTag", modifier = Modifier) {

            }
        }
    }

}