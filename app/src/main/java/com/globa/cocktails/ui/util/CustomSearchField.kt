package com.globa.cocktails.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.cocktails.ui.theme.AppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomSearchField(
    modifier: Modifier = Modifier,
    tags: List<String>,
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTagClicked: (String) -> Unit
) {
    FlowRow(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .height(30.dp)
            .requiredWidth(246.dp)
            .horizontalScroll(state = ScrollState(0)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tags.forEach {
            TagButton(
                text = it,
                modifier = Modifier.padding(start = 3.dp),
                onClickAction = {onTagClicked(it)}
            )
        }
        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = MaterialTheme.typography.bodyMedium.plus(
                TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
            ),
            singleLine = true,
        ) { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.text.isEmpty() && tags.isEmpty()) {
                    Text(
//                        text = stringResource(R.string.search_placeholder),
                        text = "Search for cocktails",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else innerTextField()
            }

        }
    }
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun CustomSearchFieldPreview() {
    val tags = emptyList<String>()
    val filter = TextFieldValue("")
    val onTextChanged: (TextFieldValue) -> Unit = { line->
        println(line)
    }
    val onTagClicked: (String) -> Unit = {}
    AppTheme {
        Surface {
            CustomSearchField(
                tags = tags,
                text = filter,
                onTextChanged = onTextChanged,
                onTagClicked = onTagClicked
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun CustomSearchFieldPreviewWithTags() {
    val tags = listOf("Rum", "Vodka")
    val filter = TextFieldValue("test")
    val onTextChanged: (TextFieldValue) -> Unit = { line->
        println(line)
    }
    val onTagClicked: (String) -> Unit = {}
    AppTheme {
        Surface {
            CustomSearchField(
                tags = tags,
                text = filter,
                onTextChanged = onTextChanged,
                onTagClicked = onTagClicked
            )
        }
    }
}