package com.globa.cocktails.feature.details.internal

import com.globa.cocktails.domain.recipedetails.RecipeDetails

sealed class CocktailUiState() {
    class Loading(): CocktailUiState()
    data class Success(val cocktail: RecipeDetails): CocktailUiState()
    data class Error(val message: String): CocktailUiState()
}