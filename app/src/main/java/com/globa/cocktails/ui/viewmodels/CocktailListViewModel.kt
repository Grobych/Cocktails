package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.*
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.CocktailFilter
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.ui.CocktailListUiState
import com.globa.cocktails.ui.CocktailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailListViewModel(
    val filterCocktailsUseCase: FilterCocktailsUseCase
) : ViewModel() {
    private val tag = this.javaClass.simpleName

    private val _uiState = MutableStateFlow(CocktailListUiState())
    val uiState : StateFlow<CocktailListUiState> = _uiState.asStateFlow()


    fun loadCocktails(filter: CocktailFilter){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                val res = filterCocktailsUseCase(filter)
                _uiState.update { it.copy(
                    isLoading = false,
                    cocktailList = res
                ) }
            } catch (e : Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }



    class Factory(private val filterCocktailsUseCase: FilterCocktailsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CocktailListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CocktailListViewModel(filterCocktailsUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}