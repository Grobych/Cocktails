package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import org.junit.Test

class FavoriteCocktailUseCaseTest {

    private val favoriteCocktailsUseCase = FavoriteCocktailsUseCase()

    @Test
    fun favoriteCocktailUseCaseTest() {
        val list = listOf(
            Cocktail(drinkName = "Test 1", isFavorite = false),
            Cocktail(drinkName = "Test 2", isFavorite = true),
            Cocktail(drinkName = "Test 3", isFavorite = true)
        )
        val favoriteList = favoriteCocktailsUseCase(list)
        favoriteList.forEach { assert(it.isFavorite) }
    }
}