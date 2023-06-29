package com.globa.cocktails.feature.cocktails

import com.globa.cocktails.domain.favorite.GetFavoritesUseCase
import com.globa.cocktails.domain.favorite.IsFavoriteCocktailUseCase
import com.globa.cocktails.domain.favorite.SetIsFavoriteUseCase
import com.globa.cocktails.domain.getcocktails.FilterCocktailsUseCase
import com.globa.cocktails.domain.getcocktails.GetAllReceipesUseCase
import com.globa.cocktails.domain.getcocktails.GetFavoriteCocktailsUseCase
import com.globa.cocktails.domain.getcocktails.RecipePreview
import com.globa.cocktails.domain.random.GetRandomRecipeUseCase
import com.globa.cocktails.domain.random.GetRandomResult
import com.globa.cocktails.feature.cocktails.internal.CocktailListUiState
import com.globa.cocktails.feature.cocktails.internal.CocktailListViewModel
import com.globa.cocktails.feature.cocktails.internal.FooterSelector
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CocktailListViewModelTest {

    private val getAllReceipesUseCase = mockk<GetAllReceipesUseCase>()
    private val setIsFavoriteUseCase = mockk<SetIsFavoriteUseCase>()
    private val isFavoriteUseCase = mockk<IsFavoriteCocktailUseCase>()
    private val getFavoritesUseCase = mockk<GetFavoritesUseCase>()

    private val favoriteCocktailsUseCase = GetFavoriteCocktailsUseCase()
    private val filterCocktailsUseCase = FilterCocktailsUseCase()
    private val randomCocktailUseCase = GetRandomRecipeUseCase()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CocktailListViewModel

    private val testList = listOf(
        RecipePreview(
            id = 1,
            name = "Test1",
            tags = listOf("test","test")
        ),
        RecipePreview(
            id = 2,
            name = "Test2",
            tags = listOf("test","test")
        ),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = CocktailListViewModel(
            getAllReceipesUseCase,
            setIsFavoriteUseCase,
            favoriteCocktailsUseCase,
            filterCocktailsUseCase,
            randomCocktailUseCase,
            isFavoriteUseCase,
            getFavoritesUseCase
        )
        coEvery { getAllReceipesUseCase() } returns flowOf(testList)
        coEvery { setIsFavoriteUseCase(any(),any()) } returns Unit
        coEvery { isFavoriteUseCase(any()) } returns flowOf(false)
        coEvery { getFavoritesUseCase() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cocktailListViewModelInitTest() = runTest {
        val uiState = viewModel.uiState
        assert(uiState.value is CocktailListUiState.Loading)

        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailListUiState.Done)
        val state = uiState.value as CocktailListUiState.Done
        assert(state.list.size == testList.size)
    }

    @Test
    fun cocktailListViewModelSelectorTest() = runTest {
        coEvery { isFavoriteUseCase("Test2") } returns flowOf(true)
        coEvery { getFavoritesUseCase() } returns flowOf(listOf("Test2"))
        testScheduler.advanceUntilIdle()

        val selectorUiState = viewModel.selectorUiState
        assert(selectorUiState.value.isAllCocktailsSelected)
        assert(!selectorUiState.value.isFavoriteSelected)
        assert(!selectorUiState.value.isMyCocktailSelected)
        val list = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list.size == testList.size)

        viewModel.selectorChanged(FooterSelector.FAVORITE_COCKTAILS)
        testScheduler.advanceUntilIdle()
        assert(!selectorUiState.value.isAllCocktailsSelected)
        assert(selectorUiState.value.isFavoriteSelected)
        assert(!selectorUiState.value.isMyCocktailSelected)
        val list2 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list2.size == 1)
        assert(list2[0].isFavorite)
        assert(list2[0].name == "Test2")

        viewModel.selectorChanged(FooterSelector.MY_COCKTAILS)
        testScheduler.advanceUntilIdle()
        assert(!selectorUiState.value.isAllCocktailsSelected)
        assert(!selectorUiState.value.isFavoriteSelected)
        assert(selectorUiState.value.isMyCocktailSelected)
        val list3 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list3.isEmpty())


        viewModel.selectorChanged(FooterSelector.ALL_COCKTAILS)
        testScheduler.advanceUntilIdle()
        assert(selectorUiState.value.isAllCocktailsSelected)
        assert(!selectorUiState.value.isFavoriteSelected)
        assert(!selectorUiState.value.isMyCocktailSelected)
    }

    @Test
    fun cocktailListViewModelFilterTest() = runTest {
        testScheduler.advanceUntilIdle()
        val filterUiState = viewModel.filterUiState

        val list = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list.size == testList.size)

        viewModel.updateFilterLine("2")
        testScheduler.advanceUntilIdle()
        val list2 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list2.size == 1)
        assert(list2[0].name == "Test2")
        assert(filterUiState.value.line == "2")

        viewModel.updateFilterLine("23")
        testScheduler.advanceUntilIdle()
        val list3 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list3.isEmpty())
        assert(filterUiState.value.line == "23")


        viewModel.updateFilterLine("")
        testScheduler.advanceUntilIdle()
        val list4 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list4 == list)
        assert(filterUiState.value.line == "")
    }

    @Test
    fun cocktailListViewModelTagTest() = runTest {
        testScheduler.advanceUntilIdle()
        val filterUiState = viewModel.filterUiState

        val list = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list.size == testList.size)

        viewModel.addFilterTag("test2")
        testScheduler.advanceUntilIdle()
        val list2 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list2.size == 1)
        assert(list2[0].name == "Test2")
        assert(filterUiState.value.tags.size == 1)
        assert(filterUiState.value.tags.first() == "test2")

        viewModel.addFilterTag("test3")
        testScheduler.advanceUntilIdle()
        val list3 = (viewModel.uiState.value as CocktailListUiState.Done).list
        assert(list3.isEmpty())
        assert(filterUiState.value.tags.size == 2)
        assert(filterUiState.value.tags[0] == "test2")
        assert(filterUiState.value.tags[1] == "test3")

        viewModel.removeFilterTag("test2")
        viewModel.removeFilterTag("test3")
        testScheduler.advanceUntilIdle()
        assert(filterUiState.value.tags.isEmpty())
        assert((viewModel.uiState.value as CocktailListUiState.Done).list == testList)
    }

    @Test
    fun cocktailListViewModelFavoriteTest() = runTest {
        testScheduler.advanceUntilIdle()

        viewModel.changeIsFavorite("Test1", true)
        testScheduler.advanceUntilIdle()
        coVerify(exactly = 1) { setIsFavoriteUseCase("Test1",true) }
    }

    @Test
    fun cocktailListViewModelRandomTest() = runTest {
        testScheduler.advanceUntilIdle()

        val randomResult = viewModel.getRandomReceipeId()
        assert(randomResult is GetRandomResult.Success)
        assert(testList.find { it.id == ((randomResult as GetRandomResult.Success).id)} != null)
    }

}