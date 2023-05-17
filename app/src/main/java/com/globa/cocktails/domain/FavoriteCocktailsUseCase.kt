package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import javax.inject.Inject

class FavoriteCocktailsUseCase @Inject constructor() {
    operator fun invoke(list: List<Cocktail>): List<Cocktail> = list.filter { it.isFavorite }
}