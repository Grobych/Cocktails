package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.ui.CocktailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: CocktailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CocktailUiState>(CocktailUiState.Loading())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val cocktailId = savedStateHandle.get<String>("cocktailId")
            val cocktail = repository.getCocktails().find { it.id == cocktailId }
            _uiState.update {
                if (cocktail!= null)
                    CocktailUiState.Success(cocktail)
                else CocktailUiState.Error("Cocktail not found!")
            }
        }
    }
}