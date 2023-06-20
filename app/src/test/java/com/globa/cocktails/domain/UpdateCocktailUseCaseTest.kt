package com.globa.cocktails.domain

import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.domain.repo.CocktailRepository
import com.globa.cocktails.domain.repo.EditLogRepository
import com.globa.cocktails.domain.editlog.AddEditLogUseCase
import com.globa.cocktails.domain.editrecipe.RecipeEditable
import com.globa.cocktails.domain.editrecipe.UpdateCocktailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UpdateCocktailUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val cocktailRepository = mockk<CocktailRepository>()
    private val editLogRepository = mockk<EditLogRepository>()
    private val addEditLogUseCase = AddEditLogUseCase(editLogRepository)
    private val getCocktailByIdUseCase = GetCocktailByIdUseCase(cocktailRepository)
    private val updateCocktailUseCase = UpdateCocktailUseCase(cocktailRepository, addEditLogUseCase, getCocktailByIdUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateCocktailUseCaseTest() = runTest {
        val repoFlow = flowOf(Cocktail(id = 1, drinkName = "Test"))
        coEvery { cocktailRepository.updateCocktail(any()) } returns Unit
        coEvery { editLogRepository.addLog(any()) } returns Unit
        coEvery { cocktailRepository.getCocktail(1) } returns repoFlow
        val cocktail = RecipeEditable(id = 1, name = "Test")
        updateCocktailUseCase(cocktail)
        coVerify { cocktailRepository.updateCocktail(repoFlow.first()) }
    }

}