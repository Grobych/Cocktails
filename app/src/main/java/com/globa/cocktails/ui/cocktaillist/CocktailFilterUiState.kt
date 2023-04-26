package com.globa.cocktails.ui.cocktaillist

import androidx.compose.ui.text.input.TextFieldValue

data class CocktailFilterUiState(
    val line: TextFieldValue = TextFieldValue(""),
    val tags: List<String> = emptyList()
)

fun CocktailFilterUiState.expandTags(): List<String> {
    return tags.plus(line.text)
}