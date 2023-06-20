package com.globa.cocktails.domain

import com.globa.cocktails.data.api.Cocktail
import com.globa.cocktails.domain.getrecipesdetails.GetRecipeDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRecipeDetailsUseCaseTest {
    private val repository = mockk<com.globa.cocktails.data.api.CocktailRepository>()
    private val getCocktailByIdUseCase = GetCocktailByIdUseCase(repository)

    private val getRecipeDetailsUseCase = GetRecipeDetailsUseCase(getCocktailByIdUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRecipeDetailUseCase() = runTest {
        val id = 1
        val testCocktail = Cocktail(
            id = id,
            drinkName = "Test",
            instructions = "Some instructions"
        )
        coEvery { repository.getCocktail(id) } returns flowOf(testCocktail)
        val result = getRecipeDetailsUseCase(id).first()

        assert(result.id == testCocktail.id)
        assert(result.instructions == testCocktail.instructions)
        assert(result.name == testCocktail.drinkName)
    }
}