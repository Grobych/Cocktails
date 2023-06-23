package com.globa.cocktails.domain.recipedetails

import com.globa.cocktails.data.cocktail.api.CocktailRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(
    private val repository: CocktailRepository

) {
    suspend operator fun invoke(id: Int) = repository.getCocktail(id)
        .map {
            RecipeDetails(
                id = it.id,
                name = it.drinkName,
                ingredients = it.ingredients,
                measures = it.measures,
                tags = mapOf(
                    it.drinkCategory to RecipeDetailsTagType.CATEGORY,
                    it.drinkGlass to RecipeDetailsTagType.GLASS
                ),
                instructions = it.instructions,
                imageURL = it.imageURL
            )
        }
}

