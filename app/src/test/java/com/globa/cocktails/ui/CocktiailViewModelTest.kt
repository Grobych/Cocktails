package com.globa.cocktails.ui

import androidx.lifecycle.SavedStateHandle
import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.ui.viewmodels.CocktailViewModel
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class CocktiailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    inner class FakeCocktailRepository @Inject constructor(): CocktailRepository {
        override suspend fun getCocktails(): List<Cocktail> {
            return listOf(
                Cocktail(drinkName = "Margarita", id = "1"),
                Cocktail(drinkName = "Martini", id = "2"),
                Cocktail(drinkName = "Cuba Libre", id = "3")
            )
        }
    }

    @Test
    fun viewModelInitTest() {
        val repository = FakeCocktailRepository()
        val savedStateHandle = SavedStateHandle(mapOf("cocktailId" to "2"))
        val viewModel = CocktailViewModel(savedStateHandle, repository)
        assert(viewModel.cocktail.value.drinkName == "Martini")
    }
}