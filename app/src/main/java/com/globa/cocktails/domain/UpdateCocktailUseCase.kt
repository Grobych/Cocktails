package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import javax.inject.Inject

class UpdateCocktailUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository
) {
    suspend operator fun invoke(cocktail: Cocktail) {
        cocktailRepository.updateCocktail(cocktail)
    }
}