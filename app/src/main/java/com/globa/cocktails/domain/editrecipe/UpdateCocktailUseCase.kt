package com.globa.cocktails.domain.editrecipe

import com.globa.cocktails.data.cocktail.api.CocktailRepository
import com.globa.cocktails.data.editlog.api.EditRecipeLog
import com.globa.cocktails.domain.GetCocktailByIdUseCase
import com.globa.cocktails.domain.editlog.AddEditLogUseCase
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject

class UpdateCocktailUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val addEditLogUseCase: AddEditLogUseCase,
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase
) {
    suspend operator fun invoke(editable: RecipeEditable) {
        val cocktail = getCocktailByIdUseCase(editable.id).first()
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