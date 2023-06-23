package com.globa.cocktails.domain.random

import org.junit.Test

class RandomCocktailUseCaseTest {

    private val randomCocktailUseCase = GetRandomRecipeUseCase()

    @Test
    fun randomCocktailUseCaseTest() {
        val ids = listOf(1,2,3,4,5)
        val res = randomCocktailUseCase(ids)

        assert(res is GetRandomResult.Success)
        val randomId = (res as GetRandomResult.Success).id
        assert(ids.contains(randomId))
        val cocktail = ids.find { it == randomId }
        assert(ids.contains(cocktail))
    }

    @Test
    fun getRandomIfListIsEmpty() {
        val ids = emptyList<Int>()
        val res = randomCocktailUseCase(ids)
        assert(res is GetRandomResult.Error)
    }
}