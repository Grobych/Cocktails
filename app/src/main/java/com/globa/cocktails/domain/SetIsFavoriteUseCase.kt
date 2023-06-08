package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SetIsFavoriteUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(id: Int, value: Boolean) {
        val cocktail = repository
            .getCocktail(id = id)
//            .catch { TODO: add custom exception }
            .first()
        repository.updateCocktail(cocktail.copy(isFavorite = value))
    }
}