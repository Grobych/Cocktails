package com.globa.cocktails.domain.getreceipes

import javax.inject.Inject

class GetFavoriteCocktailsUseCase @Inject constructor() {
    operator fun invoke(list: List<RecipePreview>): List<RecipePreview> = list.filter { it.isFavorite }
}