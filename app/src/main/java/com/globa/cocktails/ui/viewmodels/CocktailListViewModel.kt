package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.RandomCocktailUseCase
import com.globa.cocktails.ui.CocktailFilterUiState
import com.globa.cocktails.ui.CocktailListUiState
import com.globa.cocktails.ui.UiStateStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    val randomCocktailUseCase: RandomCocktailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailListUiState())
    val uiState : StateFlow<CocktailListUiState> = _uiState.asStateFlow()

    private val cocktailListState = MutableStateFlow(listOf<Cocktail>())
    private val filter = MutableStateFlow("")

    private suspend fun getCocktails() = cocktailRepository.getCocktails()

    private fun initCocktailList() {
        cocktailListState.onEach {
            _uiState.update {
                it.copy(
                    status = UiStateStatus.DONE,
                    cocktailList = cocktailListState.value
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun initFilters() {
        filter.onEach { filter ->
            _uiState.update {
                it.copy(filterUiState = CocktailFilterUiState(filter))
            }
            cocktailListState.value =
                getCocktails()
                    .filter {
                    filter.ifEmpty { true }
                    it.drinkName.contains(filter, ignoreCase = true)
                }
        }.launchIn(viewModelScope)
    }


    init {
        initFilters()
        initCocktailList()
    }

    fun updateFilter(value: String) {
        filter.update { value }
    }

    suspend fun getRandomCocktail() : Cocktail = randomCocktailUseCase()
}