package com.globa.cocktails.ui

import androidx.lifecycle.SavedStateHandle
import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.ui.cocktailinfo.CocktailUiState
import com.globa.cocktails.ui.cocktailinfo.CocktailViewModel
import org.junit.Rule
import org.junit.Test

class CocktiailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun viewModelInitTest() {
        val repository = FakeCocktailRepository()
        val savedStateHandle = SavedStateHandle(mapOf("cocktailId" to "2"))
        val viewModel = CocktailViewModel(savedStateHandle, repository)
        assert(viewModel.uiState.value is CocktailUiState.Success)
        val cocktail = (viewModel.uiState.value as CocktailUiState.Success).cocktail
        assert(cocktail.drinkName == "Martini")
    }
}