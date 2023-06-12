package com.globa.cocktails.ui.cocktaillist

data class CocktailFilterUiState(
    val line: String = "",
    val tags: List<String> = emptyList()
)

fun CocktailFilterUiState.expandTags(): List<String> {
    return tags.plus(line)
}