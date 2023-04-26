package com.globa.cocktails.ui.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomSearchField(
    tags: List<String>,
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTagClicked: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        FlowRow(
            modifier = Modifier.border(border = BorderStroke(1.dp, Color.Black)).fillMaxWidth()
        ) {
            tags.forEach {
                TagButton(
                    text = it,
                    modifier = Modifier,
                    onClickAction = {onTagClicked(it)}
                )
            }
            BasicTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.headlineMedium
            )
        }

    }
}

@Composable
@Preview
fun CustomSearchFieldPreview() {
    val tags = listOf("Rum", "Vodka")
    val filter = TextFieldValue("margar")
    val onTextChanged: (TextFieldValue) -> Unit = { line->
        println(line)
    }
    val onTagClicked: (String) -> Unit = {}
    CustomSearchField(
        tags = tags,
        text = filter,
        onTextChanged = onTextChanged,
        onTagClicked = onTagClicked
    )
}