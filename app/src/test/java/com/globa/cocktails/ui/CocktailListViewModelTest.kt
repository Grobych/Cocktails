package com.globa.cocktails.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FavoriteCocktailsUseCase
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.domain.UpdateCocktailUseCase
import com.globa.cocktails.ui.cocktaillist.CocktailListUiState
import com.globa.cocktails.ui.cocktaillist.CocktailListViewModel
import com.globa.cocktails.ui.cocktaillist.FooterSelector
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CocktailListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<CocktailRepository>()

    private val filterCocktailsUseCase = FilterCocktailsUseCase()
    private val favoriteCocktailsUseCase = FavoriteCocktailsUseCase()
    private val updateCocktailUseCase = UpdateCocktailUseCase(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun viewModelInitTest() = runTest {
        val repoCocktails = listOf(
            Cocktail(id = "1", drinkName = "1"),
            Cocktail(id = "2", drinkName = "2"),
            Cocktail(id = "3", drinkName = "3")
        )
        coEvery { repository.getCocktails() } returns flowOf(repoCocktails)
        val viewModel = CocktailListViewModel(
            cocktailRepository = repository,
            updateCocktailUseCase = updateCocktailUseCase,
            favoriteCocktailsUseCase = favoriteCocktailsUseCase,
            filterCocktailsUseCase = filterCocktailsUseCase
        )
        val uiState = viewModel.uiState.first()
        assert(uiState is CocktailListUiState.Done)
        val list = (uiState as CocktailListUiState.Done).list
        assert(repoCocktails == list)
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun viewModelInitWithExceptionTest() = runTest {
//        val errorMessage = "test error"
//        coEvery { repository.getCocktails() }.throws(Exception(errorMessage))
//        val viewModel = CocktailListViewModel(
//            cocktailRepository = repository,
//            updateCocktailUseCase = updateCocktailUseCase,
//            favoriteCocktailsUseCase = favoriteCocktailsUseCase,
//            filterCocktailsUseCase = filterCocktailsUseCase
//        )
//        try {
//            val uiState = viewModel.uiState.first()
//            assert(uiState is CocktailListUiState.Error)
//        } catch (e: Exception) {
//            assert(e.message == errorMessage)
//        }
//    }

    @Test
    fun filterTagsTest() {
        coEvery { repository.getCocktails() } returns flowOf()
        val viewModel = CocktailListViewModel(
            cocktailRepository = repository,
            updateCocktailUseCase = updateCocktailUseCase,
            favoriteCocktailsUseCase = favoriteCocktailsUseCase,
            filterCocktailsUseCase = filterCocktailsUseCase
        )
        val filterState = viewModel.filterUiState
        assert(filterState.value.tags.isEmpty())
        assert(filterState.value.line.text == "")
        viewModel.addFilterTag("Tag1")
        assert(filterState.value.tags.contains("Tag1"))
        viewModel.updateFilterLine(TextFieldValue("t"))
        assert(filterState.value.tags.contains("Tag1"))
        assert(filterState.value.line.text == "t")
        viewModel.removeFilterTag("Tag1")
        assert(filterState.value.tags.isEmpty())
    }

    @Test
    fun selectorTest() {
        coEvery { repository.getCocktails() } returns flowOf()
        val viewModel = CocktailListViewModel(
            cocktailRepository = repository,
            updateCocktailUseCase = updateCocktailUseCase,
            favoriteCocktailsUseCase = favoriteCocktailsUseCase,
            filterCocktailsUseCase = filterCocktailsUseCase
        )
        val state = viewModel.selectorUiState
        assert(state.value.isAllCocktailsSelected && !state.value.isMyCocktailSelected && !state.value.isFavoriteSelected)
        viewModel.selectorChanged(FooterSelector.FAVORITE_COCKTAILS)
        assert(!state.value.isAllCocktailsSelected && !state.value.isMyCocktailSelected && state.value.isFavoriteSelected)
        viewModel.selectorChanged(FooterSelector.MY_COCKTAILS)
        assert(!state.value.isAllCocktailsSelected && state.value.isMyCocktailSelected && !state.value.isFavoriteSelected)
    }
}