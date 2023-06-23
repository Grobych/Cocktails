package com.globa.cocktails.ui.cocktailredactor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.domain.edit.RecipeEditable
import com.globa.cocktails.domain.getrecipesdetails.GetRecipeDetailsUseCase
import com.globa.cocktails.domain.getrecipesdetails.RecipeDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailRedactorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val updateCocktailUseCase: com.globa.cocktails.domain.edit.UpdateCocktailUseCase
): ViewModel() {
    private val _cocktailState = MutableStateFlow(RecipeEditable())

    private val _uiState = MutableStateFlow<CocktailRedactorUiState>(CocktailRedactorUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow(ErrorFieldsState(false, listOf(),false))
    val errorState = _errorState.asStateFlow()

    private val _showSaveDialog = MutableStateFlow(false)
    val showSaveDialog = _showSaveDialog.asStateFlow()

    private val mode = RedactorMode.valueOf(savedStateHandle["mode"] ?: "ADD")
    private val cocktailId: Int = when (mode) {
        RedactorMode.ADD -> 0
        RedactorMode.EDIT -> savedStateHandle.get<Int>("cocktailId") ?: 0
    }

    private suspend fun selectFlow(mode: RedactorMode): Flow<RecipeEditable> = when (mode) {
        RedactorMode.ADD -> flowOf(RecipeEditable())
        RedactorMode.EDIT -> getRecipeDetailsUseCase(cocktailId).map { it.toReceipeEditable() }
    }
    private fun initCocktail(mode: RedactorMode) {
        viewModelScope.launch {
            merge(_cocktailState, selectFlow(mode))
                .onEach {cocktail ->
                    _errorState.value = checkFields(cocktail.name, cocktail.ingredients, cocktail.instructions)
                }
                .catch {
                    _uiState.value = CocktailRedactorUiState.Error(it.message?:"Unknown error")
                }
                .collect{
                    _uiState.value = CocktailRedactorUiState.Editing(it, mode)
                }

        }
    }
    init {
        initCocktail(mode = mode)
    }

    fun updateState(cocktail: RecipeEditable) {
        _cocktailState.value = cocktail
    }

    private fun checkFields(nameField: String, ingredientFields: List<String>, instrctionField: String
    ): ErrorFieldsState {
        val nameError = nameField.isBlank()
        val ingredientsError = ingredientFields.map { it.isBlank() }
        val instructionError = instrctionField.isBlank()
        return ErrorFieldsState(nameError,ingredientsError,instructionError)
    }

    fun tryToSave() {
        val state = errorState.value
        var ingredientsError = false
        state.isIngredientError.forEach { ingredientsError = ingredientsError || it }
        _showSaveDialog.value = !(ingredientsError || state.isNameError || state.isInstructionsError)
    }
    fun saveDismiss() {
        _showSaveDialog.value = false
    }
    suspend fun saveCocktail() {
        if (uiState.value is CocktailRedactorUiState.Editing) {
            _showSaveDialog.value = false
            val cocktail = (uiState.value as CocktailRedactorUiState.Editing).cocktail
            _uiState.value = CocktailRedactorUiState.Saving
            updateCocktailUseCase(cocktail)
        }
    }
}

fun RecipeDetails.toReceipeEditable() = RecipeEditable(
    id = id,
    name = name,
    ingredients = ingredients,
    measures = measures,
    instructions = instructions,
    imageURL = imageURL
)