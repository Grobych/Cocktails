package com.globa.cocktails.domain.edit

class RemoveIngredientUseCase {
    operator fun invoke(cocktail: RecipeEditable, i: Int): RecipeEditable {
        val ingredients = cocktail.ingredients
        val measures = cocktail.measures
        return if (i in ingredients.indices) {
            val resIngredients = ingredients.minus(ingredients[i])
            val resMeasures = if (i in measures.indices) measures.minus(measures[i]) else measures
            cocktail.copy(
                ingredients = resIngredients,
                measures = resMeasures
            )
        } else cocktail
    }
}