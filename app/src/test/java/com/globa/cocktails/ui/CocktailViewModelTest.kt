package com.globa.cocktails.ui

import androidx.lifecycle.SavedStateHandle
import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.UpdateCocktailUseCase
import com.globa.cocktails.ui.cocktailinfo.CocktailUiState
import com.globa.cocktails.ui.cocktailinfo.CocktailViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class CocktailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val updateCocktailUseCase = mockk<UpdateCocktailUseCase>()
    private val repository = mockk<CocktailRepository>()

    @Test
    fun viewModelInitTest() {
        val id = "1"
        val name = "Test"
        coEvery { repository.getCocktail(id) } returns flowOf(Cocktail(id = id, drinkName = name))

        val savedStateHandle = SavedStateHandle(mapOf("cocktailId" to id))
        val viewModel = CocktailViewModel(savedStateHandle, repository, updateCocktailUseCase)

        assert(viewModel.uiState.value is CocktailUiState.Success)
        val cocktail = (viewModel.uiState.value as CocktailUiState.Success).cocktail
        assert(cocktail.id == id)
        assert(cocktail.drinkName == name)
    }

    @Test
    fun incorrectCocktailTest() {
        val id = "NoSuchId"
        coEvery { repository.getCocktail(id) } returns emptyFlow()

        val savedStateHandle = SavedStateHandle(mapOf("cocktailId" to id))
        val viewModel = CocktailViewModel(savedStateHandle, repository, updateCocktailUseCase)
        assert(viewModel.uiState.value is CocktailUiState.Error)
    }

    @Test
    fun updateCocktailTest() {
        val id = "1"
        val name = "Test"
        coEvery { repository.getCocktail(id) } returns flowOf(Cocktail(id = id, drinkName = name))

        val savedStateHandle = SavedStateHandle(mapOf("cocktailId" to id))
        val viewModel = CocktailViewModel(savedStateHandle, repository, updateCocktailUseCase)

        assert(viewModel.uiState.value is CocktailUiState.Success)
        val cocktail = (viewModel.uiState.value as CocktailUiState.Success).cocktail

        val newCocktail = cocktail.copy(drinkName = "New Name")
        viewModel.updateCocktail(newCocktail)
        coVerify { updateCocktailUseCase.invoke(newCocktail) }
    }
}