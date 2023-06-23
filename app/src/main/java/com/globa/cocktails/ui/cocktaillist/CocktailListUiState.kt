package com.globa.cocktails.ui.cocktaillist

import com.globa.cocktails.domain.getcocktails.RecipePreview


sealed class CocktailListUiState() {
    class Loading(): CocktailListUiState()
    class Done(val list: List<RecipePreview>): CocktailListUiState()
    class Error(val message: String): CocktailListUiState()
}

