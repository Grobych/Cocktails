package com.globa.cocktails.ui

import com.globa.cocktails.datalayer.models.Cocktail

data class CocktailListUiState(
    val isLoading : Boolean = true,
    val isError : Boolean = false,
    val cocktailList : List<Cocktail> = listOf()
)