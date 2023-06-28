package com.globa.cocktails.feature.details.internal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.domain.favorite.GetFavoritesUseCase
import com.globa.cocktails.domain.favorite.SetIsFavoriteUseCase
import com.globa.cocktails.domain.recipedetails.GetRecipeDetailsUseCase
import com.globa.cocktails.domain.recipedetails.RecipeDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class CocktailViewModel: ViewModel() {
    abstract val uiState: StateFlow<CocktailUiState>
    abstract fun changeIsFavorite(isFavorite: Boolean)
}
@HiltViewModel
class CocktailViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val setIsFavoriteUseCase: SetIsFavoriteUseCase
) : CocktailViewModel() {

    private val _uiState = MutableStateFlow<CocktailUiState>(CocktailUiState.Loading())
    override val uiState = _uiState.asStateFlow()

    private val cocktailId = savedStateHandle.get<Int>("cocktailId")
    init {
        fetchCocktail()
    }

    private fun fetchCocktail() = viewModelScope.launch {
        if (cocktailId != null) {
            getRecipeDetailsUseCase(id = cocktailId)
                .combine(getFavoritesUseCase()) { recipeDetails: RecipeDetails, favorites: List<String> ->
                    recipeDetails.copy(isFavorite = favorites.contains(recipeDetails.name))
                }
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

    override fun changeIsFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            val state = uiState.value
            if (state is CocktailUiState.Success)
                setIsFavoriteUseCase(state.cocktail.name, isFavorite)
        }
    }
}