package com.globa.cocktails.domain.favorites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsFavoriteCocktailUseCase @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) {
    suspend operator fun invoke(name: String): Flow<Boolean> =
        getFavoritesUseCase().map {
            it.find { favorited -> favorited.name == name } != null
        }
}