package com.globa.cocktails.domain.edit

import org.junit.Test

class RemoveIngredientUseCaseTest {

    val removeIngredientUseCase = RemoveIngredientUseCase()

    @Test
    fun removeIngredientWhenMeasuresSizeEqualIngredientsSizeTest() {
        val ingredients = listOf("i1", "i2", "i3")
        val measures = listOf("m1", "m2", "m3")
        val i = 1
        assert(ingredients.size == measures.size)
        val recipeEditable = RecipeEditable(
            ingredients = ingredients,
            measures = measures
        )
        val result = removeIngredientUseCase(recipeEditable,i)
        assert(result.ingredients == ingredients.minus(ingredients[i]))
        assert(result.measures == measures.minus(measures[i]))
        assert(result.ingredients.size == ingredients.size - 1)
        assert(result.measures.size == measures.size - 1)
    }

    @Test
    fun removeIngredientWithoutMeasureTest() {
        val ingredients = listOf("i1", "i2", "i3")
        val measures = listOf("m1", "m2")
        val i = 2
        assert(ingredients.size != measures.size)
        val recipeEditable = RecipeEditable(
            ingredients = ingredients,
            measures = measures
        )
        val result = removeIngredientUseCase(recipeEditable,i)
        assert(result.ingredients == ingredients.minus(ingredients[i]))
        assert(result.measures == measures)
        assert(result.ingredients.size == ingredients.size - 1)
        assert(result.measures.size == measures.size)
    }

    @Test
    fun removeIngredientOutOfListTest() {
        val ingredients = listOf("i1", "i2", "i3")
        val measures = listOf("m1", "m2", "m3")
        val i = 5
        assert(ingredients.size == measures.size)
        assert(ingredients.lastIndex < i)
        val recipeEditable = RecipeEditable(
            ingredients = ingredients,
            measures = measures
        )
        val result = removeIngredientUseCase(recipeEditable,i)
        assert(result == recipeEditable)
    }
}