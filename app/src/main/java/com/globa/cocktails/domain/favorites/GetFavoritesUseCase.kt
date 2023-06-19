package com.globa.cocktails.domain.favorites

import com.globa.cocktails.datalayer.repository.FavoritedCocktailRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoritedCocktailRepository
) {
    suspend operator fun invoke() = repository.getFavorites()
}