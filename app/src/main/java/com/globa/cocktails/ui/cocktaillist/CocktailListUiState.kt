package com.globa.cocktails.ui.cocktaillist

import com.globa.cocktails.domain.models.ReceipePreview

sealed class CocktailListUiState() {
    class Loading(): CocktailListUiState()
    class Done(val list: List<ReceipePreview>): CocktailListUiState()
    class Error(val message: String): CocktailListUiState()
}

