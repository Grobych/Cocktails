package com.globa.cocktails.domain

import com.globa.cocktails.domain.getreceipes.GetFavoriteCocktailsUseCase
import com.globa.cocktails.domain.getreceipes.RecipePreview
import org.junit.Test

class FavoriteCocktailUseCaseTest {

    private val favoriteCocktailsUseCase = GetFavoriteCocktailsUseCase()

    @Test
    fun favoriteCocktailUseCaseTest() {
        val list = listOf(
            RecipePreview(name = "Test 1", isFavorite = false),
            RecipePreview(name = "Test 2", isFavorite = true),
            RecipePreview(name = "Test 3", isFavorite = true)
        )
        val favoriteList = favoriteCocktailsUseCase(list)
        favoriteList.forEach { assert(it.isFavorite) }
    }
}