package com.globa.cocktails.feature.cocktails.internal

data class CocktailFilterUiState(
    val line: String = "",
    val tags: List<String> = emptyList()
)

fun CocktailFilterUiState.expandTags(): List<String> {
    return tags.plus(line)
}