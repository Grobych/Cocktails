package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import org.junit.Test

class RandomCocktailUseCaseTest {

    private val randomCocktailUseCase = RandomCocktailUseCase()

    @Test
    fun randomCocktailUseCaseTest() {
        val ids = listOf("1","2","3","4","5")
        val cocktails = ids.map { Cocktail(id = it) }
        val randomId = randomCocktailUseCase(cocktails)

        assert(ids.contains(randomId))
        val cocktail = cocktails.find { it.id == randomId }
        assert(cocktails.contains(cocktail))
    }
}