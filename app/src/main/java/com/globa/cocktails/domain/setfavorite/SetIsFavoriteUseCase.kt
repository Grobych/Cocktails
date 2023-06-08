package com.globa.cocktails.domain.setfavorite

import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.GetCocktailByIdUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SetIsFavoriteUseCase @Inject constructor(
    private val repository: CocktailRepository,
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase
) {
    suspend operator fun invoke(id: Int, value: Boolean) {
        val cocktail = getCocktailByIdUseCase(id).first()
        repository.updateCocktail(cocktail.copy(isFavorite = value))
    }
}