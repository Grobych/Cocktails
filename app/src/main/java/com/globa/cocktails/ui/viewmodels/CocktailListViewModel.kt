package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.domain.RandomCocktailUseCase
import com.globa.cocktails.ui.CocktailListUiState
import com.globa.cocktails.ui.UiStateStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    val filterCocktailsUseCase: FilterCocktailsUseCase,
    val randomCocktailUseCase: RandomCocktailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailListUiState())
    val uiState : StateFlow<CocktailListUiState> = _uiState.asStateFlow()

    init {
        loadCocktails()
    }

    private fun loadCocktails(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(status = UiStateStatus.LOADING)
            }
            try {
                val res = cocktailRepository.getCocktails()
                _uiState.update { it.copy(
                    status = UiStateStatus.DONE,
                    cocktailList = res
                ) }
            } catch (e : Exception){
                _uiState.update {
                    it.copy(
                        status = UiStateStatus.ERROR,
                        errorMessage = e.toString()
                    )
                }
            }
        }
    }

    suspend fun getRandomCocktail() : Cocktail = randomCocktailUseCase()
}