package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.models.RecipeEditable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateCocktailUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository
) {
    suspend operator fun invoke(editable: RecipeEditable) {
        val cocktail = cocktailRepository
            .getCocktail(editable.id)
            .catch {  } // TODO: catch exc
            .first()
        cocktailRepository.updateCocktail(cocktail.copy(
            drinkName = editable.name,
            imageURL = editable.imageURL,
            instructions = editable.instructions,
            ingredients = editable.ingredients,
            measures = editable.measures
        ))
    }
}