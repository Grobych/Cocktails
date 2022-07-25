package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.*
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.CocktailFilter
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.domain.RandomCocktailUseCase
import com.globa.cocktails.ui.CocktailListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class CocktailListViewModel @Inject constructor(
    val filterCocktailsUseCase: FilterCocktailsUseCase,
    val randomCocktailUseCase: RandomCocktailUseCase
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

    suspend fun getRandomCocktail() : Cocktail = randomCocktailUseCase()


//    class Factory(private val filterCocktailsUseCase: FilterCocktailsUseCase,
//                    private val getRandomCocktailUseCase: RandomCocktailUseCase)
//        : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(CocktailListViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return CocktailListViewModel(
//                    filterCocktailsUseCase,
//                    getRandomCocktailUseCase) as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }
}