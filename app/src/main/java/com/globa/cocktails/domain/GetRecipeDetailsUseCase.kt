package com.globa.cocktails.domain

import com.globa.cocktails.domain.models.RecipeDetails
import com.globa.cocktails.domain.models.RecipeDetailsTagType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase
) {
    suspend operator fun invoke(id: Int) = getCocktailByIdUseCase(id)
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
                imageURL = it.imageURL,
                isFavorite = it.isFavorite
            )
        }
}

