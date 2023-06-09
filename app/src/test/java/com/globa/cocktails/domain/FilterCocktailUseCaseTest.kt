package com.globa.cocktails.domain

import com.globa.cocktails.domain.getreceipes.FilterCocktailsUseCase
import com.globa.cocktails.domain.getreceipes.RecipePreview
import org.junit.Test

class FilterCocktailUseCaseTest {

    private val filterCocktailsUseCase = FilterCocktailsUseCase()

    private val cocktails = listOf(
        RecipePreview(name = "Test", tags = listOf("Rum", "Cola")),
        RecipePreview(name = "Some", tags = listOf("Vodka")),
        RecipePreview(name = "Another", tags = listOf("Cream","Sugar","Rum"))
    )

    @Test
    fun filterByTagTest() {

        val tag = "Vodka"
        val result = filterCocktailsUseCase(cocktails, listOf(tag))
        result.forEach {
            assert(it.tags.contains(tag) || it.name.contains(tag))
        }
        cocktails.minus(result.toSet()).forEach {
            assert(!it.tags.contains(tag) && !it.name.contains(tag))
        }
    }

    @Test
    fun filterByMultipleTagTest() {
        val tags = listOf("Rum", "Tes")
        val result = filterCocktailsUseCase(cocktails, tags)
        assert(result.size == 1)
        assert(result[0].name == "Test")
    }

    @Test
    fun filterEmptyTagTest() {
        val result = filterCocktailsUseCase(cocktails, emptyList())
        assert(result.size == cocktails.size)
        assert(result == cocktails)
    }
}