package com.globa.cocktails.feature.cocktails.internal

data class CocktailSelectorUiState(
    val isAllCocktailsSelected: Boolean = true,
    val isMyCocktailSelected: Boolean = false,
    val isFavoriteSelected: Boolean = false
)
enum class FooterSelector {
    ALL_COCKTAILS, MY_COCKTAILS, FAVORITE_COCKTAILS
}