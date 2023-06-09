package com.globa.cocktails.domain

import com.globa.cocktails.domain.getrandom.GetRandomRecipeUseCase
import com.globa.cocktails.domain.getrandom.GetRandomResult
import org.junit.Test

class RandomCocktailUseCaseTest {

    private val randomCocktailUseCase = GetRandomRecipeUseCase()

    @Test
    fun randomCocktailUseCaseTest() {
        val ids = listOf(1,2,3,4,5)
        val cocktails = ids.map { Cocktail(id = it) }
        val res = randomCocktailUseCase(ids)

        assert(res is GetRandomResult.Success)
        val randomId = (res as GetRandomResult.Success).id
        assert(ids.contains(randomId))
        val cocktail = cocktails.find { it.id == randomId }
        assert(cocktails.contains(cocktail))
    }

    @Test
    fun getRandomIfListIsEmpty() {
        val ids = emptyList<Int>()
        val res = randomCocktailUseCase(ids)
        assert(res is GetRandomResult.Error)
    }
}