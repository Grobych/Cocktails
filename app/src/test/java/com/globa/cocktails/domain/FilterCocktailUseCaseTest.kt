package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import org.junit.Test

class FilterCocktailUseCaseTest {

    private val filterCocktailsUseCase = FilterCocktailsUseCase()

    @Test
    fun filterByTagTest() {
        val cocktails = listOf(
            Cocktail(drinkName = "Test", ingredients = listOf("Rum", "Cola")),
            Cocktail(drinkName = "Some", ingredients = listOf("Vodka")),
            Cocktail(drinkName = "Another", ingredients = listOf("Cream","Sugar","Rum"))
        )
        val tag = "Vodka"
        val result = filterCocktailsUseCase(cocktails, listOf(tag))
        result.forEach {
            assert(it.ingredients.contains(tag) || it.drinkName.contains(tag))
        }
        cocktails.minus(result.toSet()).forEach {
            assert(!it.ingredients.contains(tag) && !it.drinkName.contains(tag))
        }
    }

    @Test
    fun filterByMultipleTagTest() {
        val cocktails = listOf(
            Cocktail(drinkName = "Test", ingredients = listOf("Rum", "Cola")),
            Cocktail(drinkName = "Some", ingredients = listOf("Vodka")),
            Cocktail(drinkName = "Another", ingredients = listOf("Cream","Sugar","Rum"))
        )
        val tags = listOf("Rum", "Tes")
        val result = filterCocktailsUseCase(cocktails, tags)
        assert(result.size == 1)
        assert(result[0].drinkName == "Test")
    }

    @Test
    fun filterEmptyTagTest() {
        val cocktails = listOf(
            Cocktail(drinkName = "Test", ingredients = listOf("Rum", "Cola")),
            Cocktail(drinkName = "Some", ingredients = listOf("Vodka")),
            Cocktail(drinkName = "Another", ingredients = listOf("Cream","Sugar","Rum"))
        )
        val result = filterCocktailsUseCase(cocktails, emptyList())
        assert(result.size == cocktails.size)
        assert(result == cocktails)
    }
}