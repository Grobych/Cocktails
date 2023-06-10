package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.getreceipes.GetAllReceipesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAllRecipesUseCaseTest {
    private val repository = mockk<CocktailRepository>()
    private val getAllReceipesUseCase = GetAllReceipesUseCase(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllRecipesTest() = runTest {
        val testCocktails = listOf(
            Cocktail(id = 1, drinkName = "T1"),
            Cocktail(id = 2, drinkName = "T2")
        )
        coEvery { repository.getCocktails() } returns flowOf(testCocktails)

        val result = getAllReceipesUseCase()
        val cocktails = result.first()
        assert(cocktails.size == testCocktails.size)
        assert(cocktails[0].id == testCocktails[0].id)
        assert(cocktails[1].name == testCocktails[1].drinkName)
    }
}