package com.globa.cocktails.ui

import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.ui.viewmodels.CocktailListViewModel
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class CocktailListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    inner class FakeGoodCocktailRepository @Inject constructor(): CocktailRepository {
        override suspend fun getCocktails(): List<Cocktail> {
            return listOf(
                Cocktail(drinkName = "Margarita", id = "1"),
                Cocktail(drinkName = "Martini", id = "2"),
                Cocktail(drinkName = "Cuba Libre", id = "3")
            )
        }
    }
    inner class FakeBadCocktailRepository @Inject constructor(): CocktailRepository {
        override suspend fun getCocktails(): List<Cocktail> {
            return emptyList()
        }
    }

    @Test
    fun isCocktailListInitialized() {
        val fakeGoogRepository = FakeGoodCocktailRepository()
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
    fun testFilter() {
        val fakeGoogRepository = FakeGoodCocktailRepository()
        val viewModel = CocktailListViewModel(fakeGoogRepository)
        assert(viewModel.uiState.value.cocktailList.isNotEmpty())
        viewModel.updateFilter("m")
        assert(viewModel.uiState.value.cocktailList.size == 2)
        viewModel.updateFilter("mart")
        assert(viewModel.uiState.value.cocktailList.size == 1)
        viewModel.updateFilter("martt")
        assert(viewModel.uiState.value.cocktailList.isEmpty())
        viewModel.updateFilter("cu")
        assert(viewModel.uiState.value.cocktailList.size == 1)
        assert(viewModel.uiState.value.filterUiState.filter == "cu")
        assert(viewModel.uiState.value.cocktailList.findLast { it.id == "3" } != null)
    }

    @Test
    fun testGetRandomCocktail() {
        val repository = FakeGoodCocktailRepository()
        val viewModel = CocktailListViewModel(repository)
        val res = viewModel.getRandomCocktail()
        assert(listOf("1","2","3").contains(res))
    }
}