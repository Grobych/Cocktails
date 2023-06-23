package com.globa.cocktails.domain.favorite

import com.globa.cocktails.data.favorite.api.Favorited
import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
import javax.inject.Inject

class SetIsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoritedCocktailRepository
) {
    suspend operator fun invoke(name: String, value: Boolean) {
        if (value) favoriteRepository.add(Favorited(name = name))
        else favoriteRepository.remove(Favorited(name = name))
    }
}