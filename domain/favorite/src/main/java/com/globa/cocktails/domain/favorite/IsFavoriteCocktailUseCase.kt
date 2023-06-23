package com.globa.cocktails.domain.favorite

import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsFavoriteCocktailUseCase @Inject constructor(
    private val repository: FavoritedCocktailRepository
) {
    suspend operator fun invoke(name: String): Flow<Boolean> =
        repository.getFavorites().map {
            it.find { favorited -> favorited.name == name } != null
        }
}