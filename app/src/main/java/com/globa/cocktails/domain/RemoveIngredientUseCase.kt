package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail

class RemoveIngredientUseCase {
    operator fun invoke(cocktail: Cocktail, i: Int): Cocktail {
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