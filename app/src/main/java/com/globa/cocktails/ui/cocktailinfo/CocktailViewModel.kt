package com.globa.cocktails.ui.cocktailinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.domain.getrecipesdetails.GetRecipeDetailsUseCase
import com.globa.cocktails.domain.setfavorite.SetIsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val setIsFavoriteUseCase: SetIsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CocktailUiState>(CocktailUiState.Loading())
    val uiState = _uiState.asStateFlow()

    private val cocktailId = savedStateHandle.get<Int>("cocktailId")
    init {
        fetchCocktail()
    }

    private fun fetchCocktail() = viewModelScope.launch {
        if (cocktailId != null) {
            getRecipeDetailsUseCase(id = cocktailId)
                .onEach {cocktail ->
                    _uiState.update {
                        CocktailUiState.Success(cocktail)
                    }
                }
                .onEmpty {
                    _uiState.update {
                        CocktailUiState.Error("Empty")
                    }
                }.collect()
        } else {
            _uiState.update {
                CocktailUiState.Error("Incorrect ID")
            }
        }
    }

    fun changeIsFavorite(value: Boolean) = viewModelScope.launch {
        setIsFavoriteUseCase(cocktailId!!,value)
    }
}