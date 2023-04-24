package com.globa.cocktails.ui.cocktailinfo

import com.globa.cocktails.datalayer.models.Cocktail

//data class CocktailUiState(
//    val cocktail: Cocktail
//)

sealed class CocktailUiState() {
    class Loading(): CocktailUiState()
    data class Success(val cocktail: Cocktail): CocktailUiState()
    data class Error(val message: String): CocktailUiState()
}