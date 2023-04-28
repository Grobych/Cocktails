package com.globa.cocktails.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.ui.cocktaillist.CocktailListViewModel
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class CocktailListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    inner class FakeBadCocktailRepository @Inject constructor(): CocktailRepository {
        override suspend fun getCocktails(): List<Cocktail> {
            return emptyList()
        }
    }

    @Test
    fun isCocktailListInitialized() {
        val fakeGoogRepository = FakeCocktailRepository()
        val viewModel = CocktailListViewModel(fakeGoogRepository)
        assert(viewModel.uiState.value.cocktailList.isNotEmpty())
        assert(viewModel.uiState.value.cocktailList.findLast { it.drinkName == "Cuba Libre" } != null)
    }

    @Test
    fun testEmptyResponceFromRepository() {
        val fakeBadCocktailRepository = FakeBadCocktailRepository()
        val viewModel = CocktailListViewModel(fakeBadCocktailRepository)
        assert(viewModel.uiState.value.cocktailList.isEmpty())
    }

    @Test
    fun testFilterLine() {
        val fakeGoogRepository = FakeCocktailRepository()
        val viewModel = CocktailListViewModel(fakeGoogRepository)
        assert(viewModel.uiState.value.cocktailList.isNotEmpty())
        viewModel.updateFilterLine(TextFieldValue("m"))
        assert(viewModel.uiState.value.cocktailList.size == 2)
        viewModel.updateFilterLine(TextFieldValue("mart"))
        assert(viewModel.uiState.value.cocktailList.size == 1)
        viewModel.updateFilterLine(TextFieldValue("martt"))
        assert(viewModel.uiState.value.cocktailList.isEmpty())
        viewModel.updateFilterLine(TextFieldValue("cu"))
        assert(viewModel.uiState.value.cocktailList.size == 1)
        assert(viewModel.filterUiState.value.line.text == "cu")
        assert(viewModel.uiState.value.cocktailList.findLast { it.id == "3" } != null)
    }

    @Test
    fun testGetRandomCocktail() {
        val repository = FakeCocktailRepository()
        val viewModel = CocktailListViewModel(repository)
        val res = viewModel.getRandomCocktail()
        assert(listOf("1","2","3").contains(res))
    }
}