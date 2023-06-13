package com.globa.cocktails.ui.util

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.cocktails.R
import com.globa.cocktails.ui.theme.AppTheme

@Composable
fun CustomSearchField(
    modifier: Modifier = Modifier,
    tags: List<String>,
    text: String,
    onTextChanged: (String) -> Unit,
    onTagClicked: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(shape = MaterialTheme.shapes.extraLarge)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(state = scrollState),
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
                onValueChange = {onTextChanged(it)},
                textStyle = MaterialTheme.typography.bodyMedium.plus(
                    TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                ),
                singleLine = true,
                modifier = Modifier.requiredWidth(300.dp)
            ) { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty() && tags.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_placeholder),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    innerTextField()
                }
            }
        }
        if (text.isEmpty()) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
                    .size(22.dp)
            )
        }
    }
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun CustomSearchFieldPreview() {
    val tags = emptyList<String>()
    val filter = ""
    val onTextChanged: (String) -> Unit = { line->
        println(line)
    }
    val onTagClicked: (String) -> Unit = {}
    AppTheme {
        Surface {
            CustomSearchField(
                modifier = Modifier.width(300.dp),
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
    val filter = "test"
    val onTextChanged: (String) -> Unit = { line->
        println(line)
    }
    val onTagClicked: (String) -> Unit = {}
    AppTheme {
        Surface {
            CustomSearchField(
                modifier = Modifier.width(300.dp),
                tags = tags,
                text = filter,
                onTextChanged = onTextChanged,
                onTagClicked = onTagClicked
            )
        }
    }
}