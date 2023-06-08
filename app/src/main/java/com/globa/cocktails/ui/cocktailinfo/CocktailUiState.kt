package com.globa.cocktails.ui.cocktailinfo

import com.globa.cocktails.domain.models.RecipeDetails

//data class CocktailUiState(
//    val cocktail: Cocktail
//)

sealed class CocktailUiState() {
    class Loading(): CocktailUiState()
    data class Success(val cocktail: RecipeDetails): CocktailUiState()
    data class Error(val message: String): CocktailUiState()
}