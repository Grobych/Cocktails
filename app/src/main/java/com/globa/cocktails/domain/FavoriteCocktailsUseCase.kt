package com.globa.cocktails.domain

import com.globa.cocktails.domain.models.ReceipePreview
import javax.inject.Inject

class FavoriteCocktailsUseCase @Inject constructor() {
    operator fun invoke(list: List<ReceipePreview>): List<ReceipePreview> = list.filter { it.isFavorite }
}