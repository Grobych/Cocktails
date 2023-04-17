package com.globa.cocktails.ui

import androidx.lifecycle.SavedStateHandle
import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.ui.viewmodels.CocktailViewModel
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
        assert(viewModel.cocktail.value.drinkName == "Martini")
    }
}