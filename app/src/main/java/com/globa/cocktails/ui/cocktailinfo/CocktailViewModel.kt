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
        viewModelScope.launch {
            fetchCocktail()
        }
    }

    private suspend fun fetchCocktail() {
        if (cocktailId != null) {
            val cocktail = repository.getCocktail(cocktailId)
            _uiState.update {
                CocktailUiState.Success(cocktail)
            }
        }
    }

    fun updateCocktail(cocktail: Cocktail) = viewModelScope.launch {
        updateCocktailUseCase(cocktail)
        fetchCocktail()
    }
}