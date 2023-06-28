package com.globa.cocktails.feature.details

import androidx.lifecycle.SavedStateHandle
import com.globa.cocktails.domain.favorite.GetFavoritesUseCase
import com.globa.cocktails.domain.favorite.SetIsFavoriteUseCase
import com.globa.cocktails.domain.recipedetails.GetRecipeDetailsUseCase
import com.globa.cocktails.domain.recipedetails.RecipeDetails
import com.globa.cocktails.feature.details.internal.CocktailUiState
import com.globa.cocktails.feature.details.internal.CocktailViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CocktailViewModelTest {
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val getRecipeDetailsUseCase = mockk<GetRecipeDetailsUseCase>()
    private val getFavoritesUseCase = mockk<GetFavoritesUseCase>()
    private val setIsFavoriteUseCase = mockk<SetIsFavoriteUseCase>()

    private val cocktailId = 1
    private val testRecipeDetails = RecipeDetails(
        id = cocktailId,
        name = "Test",
        ingredients = listOf("in"),
        measures = listOf("1"),
        tags = emptyMap(),
        instructions = "test test test"
    )
    private val flow = flowOf(testRecipeDetails)

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cocktailViewModelInitNotFavoriteTest() = runTest {
        coEvery { getFavoritesUseCase() } returns flowOf(emptyList())
        val viewModel = CocktailViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            getFavoritesUseCase,
            setIsFavoriteUseCase
        )
        val uiState = viewModel.uiState
        assert(uiState.value is CocktailUiState.Loading)
        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailUiState.Success)
        val recipe = (uiState.value as CocktailUiState.Success).cocktail
        assert(recipe.name == testRecipeDetails.name)
        assert(recipe.instructions == testRecipeDetails.instructions)
        assert(recipe.id == testRecipeDetails.id)
        assert(!recipe.isFavorite)
    }

    @Test
    fun cocktailViewModelInitFavoriteTest() = runTest {
        coEvery { getFavoritesUseCase() } returns flowOf(listOf(testRecipeDetails.name))
        val viewModel = CocktailViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            getFavoritesUseCase,
            setIsFavoriteUseCase
        )
        val uiState = viewModel.uiState
        assert(uiState.value is CocktailUiState.Loading)
        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailUiState.Success)
        val recipe = (uiState.value as CocktailUiState.Success).cocktail
        assert(recipe.name == testRecipeDetails.name)
        assert(recipe.instructions == testRecipeDetails.instructions)
        assert(recipe.id == testRecipeDetails.id)
        assert(recipe.isFavorite)
    }

    @Test
    fun cocktailViewModelNullIdInitTest() = runTest {
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns null
        val viewModel = CocktailViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            getFavoritesUseCase,
            setIsFavoriteUseCase
        )
        val uiState = viewModel.uiState
        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailUiState.Error)
    }

    @Test
    fun cocktailViewModelIncorrectIdInitTest() = runTest {
        coEvery { getFavoritesUseCase() } returns flowOf(emptyList())
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns 5
        coEvery { getRecipeDetailsUseCase(5) } returns emptyFlow()
        val viewModel = CocktailViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            getFavoritesUseCase,
            setIsFavoriteUseCase
        )
        val uiState = viewModel.uiState
        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailUiState.Error)
    }

    @Test
    fun cocktailViewModelSetFavoriteTest() = runTest {
        coEvery { getFavoritesUseCase() } returns flowOf(emptyList())
        coEvery { setIsFavoriteUseCase(testRecipeDetails.name, true) } returns Unit

        val viewModel = CocktailViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            getFavoritesUseCase,
            setIsFavoriteUseCase
        )
        val uiState = viewModel.uiState
        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailUiState.Success)
        val recipe = (uiState.value as CocktailUiState.Success).cocktail
        assert(!recipe.isFavorite)

        viewModel.changeIsFavorite(recipe.name, true)
        testScheduler.advanceUntilIdle()
        coVerify(exactly = 1) { setIsFavoriteUseCase(testRecipeDetails.name, true) }
    }
}