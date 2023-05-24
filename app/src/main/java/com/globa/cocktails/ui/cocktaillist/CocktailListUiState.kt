package com.globa.cocktails.ui.cocktaillist

import com.globa.cocktails.datalayer.models.Cocktail

sealed class CocktailListUiState() {
    class Loading(): CocktailListUiState()
    class Done(val list: List<Cocktail>): CocktailListUiState()
    class Error(val message: String): CocktailListUiState()
}

