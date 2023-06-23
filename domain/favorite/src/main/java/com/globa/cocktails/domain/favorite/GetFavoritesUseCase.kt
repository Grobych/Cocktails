package com.globa.cocktails.domain.favorite

import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoritedCocktailRepository
) {
    suspend operator fun invoke() = repository.getFavorites()
}