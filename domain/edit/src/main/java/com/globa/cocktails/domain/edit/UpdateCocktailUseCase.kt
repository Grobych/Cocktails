package com.globa.cocktails.domain.edit

import com.globa.cocktails.data.cocktail.api.CocktailRepository
import com.globa.cocktails.data.editlog.api.EditRecipeLog
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject

class UpdateCocktailUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val addEditLogUseCase: AddEditLogUseCase,
) {
    suspend operator fun invoke(editable: RecipeEditable) {
        val cocktail = cocktailRepository.getCocktail(editable.id).first()
        cocktailRepository.updateCocktail(cocktail.copy(
            drinkName = editable.name,
            imageURL = editable.imageURL,
            instructions = editable.instructions,
            ingredients = editable.ingredients,
            measures = editable.measures
        ))
        addEditLogUseCase(
            EditRecipeLog(
                name = cocktail.drinkName,
                dateTime = Calendar.getInstance().timeInMillis
            )
        )
    }
}