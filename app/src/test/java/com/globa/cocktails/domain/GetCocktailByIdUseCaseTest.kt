package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCocktailByIdUseCaseTest {
    private val repository = mockk<CocktailRepository>()
    private val getCocktailByIdUseCase = GetCocktailByIdUseCase(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCocktailByIdTest() = runTest {
        val id = 1
        val testCocktail = Cocktail(
            id = id,
            drinkName = "Test",
            instructions = "Some instructions"
        )
        coEvery { repository.getCocktail(id) } returns flowOf(testCocktail)

        val res = getCocktailByIdUseCase(id)
        assert(res.first() == testCocktail)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCocktailByIncorrectIdTest() = runTest {
        //TODO: add out of bound id test
    }
}