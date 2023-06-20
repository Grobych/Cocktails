package com.globa.cocktails.domain.favorites

import com.globa.cocktails.data.api.Favorited
import javax.inject.Inject

class SetIsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: com.globa.cocktails.data.api.FavoritedCocktailRepository
) {
    suspend operator fun invoke(name: String, value: Boolean) {
        if (value) favoriteRepository.add(Favorited(name = name))
        else favoriteRepository.remove(Favorited(name = name))
    }
}