package com.globa.cocktails.ui.cocktailredactor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailRedactorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CocktailRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow<CocktailRedactorUiState>(CocktailRedactorUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val mode = savedStateHandle.get<RedactorMode>("mode") ?: RedactorMode.ADD
    private val cocktailId: String = when (mode) {
        RedactorMode.ADD -> {""}
        RedactorMode.EDIT -> {
            savedStateHandle.get<String>("cocktailId") ?: ""
        }
    }


    init {
        viewModelScope.launch {
            if(cocktailId =="") {
                _uiState.value = CocktailRedactorUiState.Editing(
                    cocktail = Cocktail(),
                    mode = mode
                )

            } else {
                repository.getCocktail(cocktailId)
                    .catch {
                        _uiState.value = CocktailRedactorUiState.Error(it.message?:"Unknown error")
                    }
                    .collect{
                        _uiState.value = CocktailRedactorUiState.Editing(
                                cocktail = it,
                                mode = mode
                            )
                        }
            }
        }
    }

    fun updateState(cocktail: Cocktail) {
        if (_uiState.value is CocktailRedactorUiState.Editing) {
            _uiState.value = CocktailRedactorUiState.Editing(cocktail, mode)
        }
    }

    suspend fun saveCocktail() {
        if (uiState.value is CocktailRedactorUiState.Editing) {
            val cocktail = (uiState.value as CocktailRedactorUiState.Editing).cocktail
            _uiState.value = CocktailRedactorUiState.Saving
            repository.saveCocktail(cocktail)
        }
    }
}