package com.globa.cocktails.ui.cocktailredactor

import com.globa.cocktails.datalayer.models.Cocktail

sealed class CocktailRedactorUiState{
    object Loading: CocktailRedactorUiState()
    class Editing(val cocktail: Cocktail, val mode: RedactorMode): CocktailRedactorUiState()
    object Saving: CocktailRedactorUiState()
    class Error(val message: String): CocktailRedactorUiState()
}
enum class RedactorMode {ADD, EDIT}
