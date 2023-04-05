package com.globa.cocktails.ui

import com.globa.cocktails.datalayer.models.Cocktail

data class CocktailListUiState(
    val status: UiStateStatus = UiStateStatus.LOADING,
    val cocktailList: List<Cocktail> = listOf(),
    val errorMessage: String = "",
    val filterUiState: CocktailFilterUiState = CocktailFilterUiState()
)

data class CocktailFilterUiState(
    val filter: String = ""
)