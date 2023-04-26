package com.globa.cocktails.ui.cocktaillist

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.ui.UiStateStatus

data class CocktailListUiState(
    val status: UiStateStatus = UiStateStatus.LOADING,
    val cocktailList: List<Cocktail> = listOf(),
    val errorMessage: String = ""
)

