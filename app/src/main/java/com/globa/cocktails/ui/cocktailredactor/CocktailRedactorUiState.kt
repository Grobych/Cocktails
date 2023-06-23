package com.globa.cocktails.ui.cocktailredactor

import com.globa.cocktails.domain.edit.RecipeEditable


sealed class CocktailRedactorUiState{
    object Loading: CocktailRedactorUiState()
    class Editing(val cocktail: RecipeEditable, val mode: RedactorMode): CocktailRedactorUiState()
    object Saving: CocktailRedactorUiState()
    class Error(val message: String): CocktailRedactorUiState()
}
enum class RedactorMode {ADD, EDIT}
data class ErrorFieldsState(val isNameError: Boolean, val isIngredientError: List<Boolean>, val isInstructionsError: Boolean)

