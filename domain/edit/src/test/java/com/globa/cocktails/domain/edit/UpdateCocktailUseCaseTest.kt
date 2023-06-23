package com.globa.cocktails.domain.edit

import com.globa.cocktails.data.cocktail.api.Cocktail
import com.globa.cocktails.data.cocktail.api.CocktailRepository
import com.globa.cocktails.data.editlog.api.EditLogRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateCocktailUseCaseTest {

    private val cocktailRepository = mockk<CocktailRepository>()
    private val editLogRepository = mockk<EditLogRepository>()
    private val addEditLogUseCase = AddEditLogUseCase(editLogRepository)
    private val updateCocktailUseCase = UpdateCocktailUseCase(cocktailRepository, addEditLogUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateCocktailUseCaseTest() = runTest {
        val repoFlow = flowOf(
            Cocktail(
                id = 1,
                drinkName = "Test"
            )
        )
        coEvery { cocktailRepository.updateCocktail(any()) } returns Unit
        coEvery { editLogRepository.addLog(any()) } returns Unit
        coEvery { cocktailRepository.getCocktail(1) } returns repoFlow
        val cocktail = RecipeEditable(id = 1, name = "Test")
        updateCocktailUseCase(cocktail)
        coVerify { cocktailRepository.updateCocktail(repoFlow.first()) }
    }

}