package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.*
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.ui.CocktailListUiState
import com.globa.cocktails.ui.CocktailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailListViewModel(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val tag = this.javaClass.simpleName

    private val _uiState = MutableStateFlow(CocktailListUiState())
    val uiState : StateFlow<CocktailListUiState> = _uiState.asStateFlow()


    fun loadCocktails(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                val res = cocktailRepository.getCocktails()
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



    class Factory(private val repository: CocktailRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CocktailListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CocktailListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}