package com.globa.cocktails.ui.cocktailinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.UpdateCocktailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CocktailRepository,
    private val updateCocktailUseCase: UpdateCocktailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CocktailUiState>(CocktailUiState.Loading())
    val uiState = _uiState.asStateFlow()

    private val cocktailId = savedStateHandle.get<String>("cocktailId")
    init {
        fetchCocktail()
    }

    private fun fetchCocktail() {
        if (cocktailId != null) {
            repository
                .getCocktail(cocktailId)
                .onEach {cocktail ->
                    _uiState.update {
                        CocktailUiState.Success(cocktail)
                    }
                }
                .onEmpty {
                    _uiState.update {
                        CocktailUiState.Error("Empty")
                    }
                }.launchIn(viewModelScope)
        } else {
            _uiState.update {
                CocktailUiState.Error("Incorrect ID")
            }
        }
    }

    fun updateCocktail(cocktail: Cocktail) = viewModelScope.launch {
        updateCocktailUseCase(cocktail)
    }
}