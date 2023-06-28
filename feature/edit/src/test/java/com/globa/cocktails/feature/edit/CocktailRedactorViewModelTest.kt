package com.globa.cocktails.feature.edit

import androidx.lifecycle.SavedStateHandle
import com.globa.cocktails.domain.edit.UpdateCocktailUseCase
import com.globa.cocktails.domain.recipedetails.GetRecipeDetailsUseCase
import com.globa.cocktails.domain.recipedetails.RecipeDetails
import com.globa.cocktails.feature.edit.internal.CocktailRedactorUiState
import com.globa.cocktails.feature.edit.internal.CocktailRedactorViewModel
import com.globa.cocktails.feature.edit.internal.RedactorMode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CocktailRedactorViewModelTest {
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val getRecipeDetailsUseCase = mockk<GetRecipeDetailsUseCase>()
    private val updateCocktailUseCase = mockk<UpdateCocktailUseCase>()

    private val dispatcher = StandardTestDispatcher()

    private val cocktailId = 1
    private val testRecipeDetails = RecipeDetails(
        id = cocktailId,
        name = "Test",
        ingredients = listOf("in"),
        measures = listOf("1"),
        tags = emptyMap(),
        instructions = "test test test"
    )
    private val flow = flowOf(
        testRecipeDetails
    )

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cocktailRedactorViewModelInitTest() = runTest {
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        val uiState = viewModel.uiState
        assert(uiState.value is CocktailRedactorUiState.Loading)
        testScheduler.advanceUntilIdle()
        assert(uiState.value is CocktailRedactorUiState.Editing)
        assert((uiState.value as CocktailRedactorUiState.Editing).cocktail.name == "Test")
    }

    @Test
    fun cocktailRedactorViewModelUpdateEditableTest() = runTest {
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        testScheduler.advanceUntilIdle()
        val currentEditable = (viewModel.uiState.value as CocktailRedactorUiState.Editing).cocktail
        viewModel.updateEditable(currentEditable.copy(instructions = currentEditable.instructions + "1"))
        testScheduler.advanceUntilIdle()
        assert(viewModel.uiState.value is CocktailRedactorUiState.Editing)
        assert(!viewModel.errorState.value.isInstructionsError)
        assert(!viewModel.errorState.value.isNameError)
        viewModel.errorState.value.isIngredientError.forEach { assert(!it) }
        assert((viewModel.uiState.value as CocktailRedactorUiState.Editing).cocktail.instructions == testRecipeDetails.instructions + "1")
    }

    @Test
    fun cocktailRedactorViewModelIncorrectUpdateEditableTest() = runTest {
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        testScheduler.advanceUntilIdle()

        assert(!viewModel.errorState.value.isInstructionsError)
        assert(!viewModel.errorState.value.isNameError)
        viewModel.errorState.value.isIngredientError.forEach { assert(!it) }

        val currentEditable = (viewModel.uiState.value as CocktailRedactorUiState.Editing).cocktail

        //empty instructions
        viewModel.updateEditable(currentEditable.copy(instructions = ""))
        testScheduler.advanceUntilIdle()
        assert(viewModel.errorState.value.isInstructionsError)
        assert(!viewModel.errorState.value.isNameError)
        viewModel.errorState.value.isIngredientError.forEach { assert(!it) }

        //empty ingredient name
        viewModel.updateEditable(currentEditable.copy(ingredients = listOf("")))
        testScheduler.advanceUntilIdle()
        assert(!viewModel.errorState.value.isInstructionsError)
        assert(!viewModel.errorState.value.isNameError)
        assert(viewModel.errorState.value.isIngredientError.first())

        //empty name
        viewModel.updateEditable(currentEditable.copy(name = ""))
        testScheduler.advanceUntilIdle()
        assert(!viewModel.errorState.value.isInstructionsError)
        assert(viewModel.errorState.value.isNameError)
        viewModel.errorState.value.isIngredientError.forEach { assert(!it) }
    }

    @Test
    fun cocktailRedactorViewModelRequestToSaveTest() = runTest {
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        testScheduler.advanceUntilIdle()

        assert(!viewModel.showSaveDialog.value)

        viewModel.requestToSaveRecipe()
        assert(viewModel.showSaveDialog.value)
    }

    @Test
    fun cocktailRedactorViewModelRequestToSaveIfEmptyFieldTest() = runTest {
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow.map { it.copy(name = "") }
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        testScheduler.advanceUntilIdle()

        assert(!viewModel.showSaveDialog.value)

        viewModel.requestToSaveRecipe()
        assert(!viewModel.showSaveDialog.value)
    }

    @Test
    fun coctailRedactorViewModelApplySavingTest() = runTest {
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
        coEvery { updateCocktailUseCase(any()) } returns Unit
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        testScheduler.advanceUntilIdle()

        viewModel.requestToSaveRecipe()
        assert(viewModel.showSaveDialog.value)

        assert(viewModel.uiState.value is CocktailRedactorUiState.Editing)

        viewModel.saveApply()
        assert(!viewModel.showSaveDialog.value)
        assert(viewModel.uiState.value is CocktailRedactorUiState.Saving)
        coVerify { updateCocktailUseCase(any()) }
    }

    @Test
    fun cocktailRedactorViewModelDenySavingTest() = runTest {
        coEvery { savedStateHandle.get<String>("mode") } returns RedactorMode.EDIT.name
        coEvery { savedStateHandle.get<Int>("cocktailId") } returns cocktailId
        coEvery { getRecipeDetailsUseCase(cocktailId) } returns flow
        coEvery { updateCocktailUseCase(any()) } returns Unit
        val viewModel = CocktailRedactorViewModel(
            savedStateHandle,
            getRecipeDetailsUseCase,
            updateCocktailUseCase
        )
        testScheduler.advanceUntilIdle()

        viewModel.requestToSaveRecipe()
        assert(viewModel.showSaveDialog.value)

        assert(viewModel.uiState.value is CocktailRedactorUiState.Editing)

        viewModel.saveDismiss()
        assert(!viewModel.showSaveDialog.value)
        assert(viewModel.uiState.value is CocktailRedactorUiState.Editing)
        coVerify(exactly = 0) { updateCocktailUseCase(any()) }
    }
}