package com.globa.cocktails.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TagButton(
    modifier: Modifier = Modifier,
    text: String,
    onClickAction: () -> Unit
) {
    Box(modifier = modifier) {
        Button(onClick = onClickAction) {
            Text(text = text, fontSize = MaterialTheme.typography.labelSmall.fontSize)
        }
    }
}