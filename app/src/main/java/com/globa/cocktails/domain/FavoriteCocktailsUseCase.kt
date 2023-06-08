package com.globa.cocktails.domain

import com.globa.cocktails.domain.models.RecipePreview
import javax.inject.Inject

class FavoriteCocktailsUseCase @Inject constructor() {
    operator fun invoke(list: List<RecipePreview>): List<RecipePreview> = list.filter { it.isFavorite }
}