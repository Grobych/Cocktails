package com.globa.cocktails.domain.getcocktails

import javax.inject.Inject

//TODO: rewrite
class GetFavoriteCocktailsUseCase @Inject constructor() {
    operator fun invoke(list: List<RecipePreview>): List<RecipePreview> = list.filter { it.isFavorite }
}