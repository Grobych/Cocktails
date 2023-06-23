package com.globa.cocktails.domain.recipedetails

import com.globa.cocktails.data.cocktail.api.Cocktail
import com.globa.cocktails.data.cocktail.api.CocktailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRecipeDetailsUseCaseTest {
    private val repository = mockk<CocktailRepository>()

    private val getRecipeDetailsUseCase = GetRecipeDetailsUseCase(repository)

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