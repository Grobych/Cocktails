package com.globa.cocktails.ui.cocktaillist

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FavoriteCocktailsUseCase
import com.globa.cocktails.domain.FilterCocktailsUseCase.filterByTags
import com.globa.cocktails.domain.UpdateCocktailUseCase
import com.globa.cocktails.ui.UiStateStatus
import com.globa.cocktails.utils.contains
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val updateCocktailUseCase: UpdateCocktailUseCase,
    private val favoriteCocktailsUseCase: FavoriteCocktailsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailListUiState())
    val uiState : StateFlow<CocktailListUiState> = _uiState.asStateFlow()

    private val _filterUiState = MutableStateFlow(CocktailFilterUiState())
    val filterUiState = _filterUiState.asStateFlow()

    private val _selectorUiState = MutableStateFlow(CocktailSelectorUiState())
    val selectorUiState = _selectorUiState.asStateFlow()

    private fun initCocktailList() = viewModelScope.launch {
        cocktailRepository
            .getCocktails()
            .combine(selectorUiState) { cocktails: List<Cocktail>, state: CocktailSelectorUiState ->
                if (state.isFavoriteSelected) favoriteCocktailsUseCase(cocktails) else cocktails
            }
            .combine(filterUiState) { cocktails: List<Cocktail>, filter: CocktailFilterUiState ->
                if (filter.tags.isEmpty() && filter.line.text.isEmpty()) cocktails
                else cocktails.filterByTags(filter.expandTags())
            }
            .collect {
                _uiState.update { uiState ->
                    uiState.copy(
                        status = UiStateStatus.DONE,
                        cocktailList = it
                    )
                }
            }
    }

    init {
        initCocktailList()
    }

    fun updateFilterLine(line: TextFieldValue) {
        _filterUiState.update { it.copy(line = line) }
    }
    fun addFilterTag(tag: String) {
        if (!filterUiState.value.tags.contains(tag, ignoreCase = true))
            _filterUiState.update {
                it.copy(tags = it.tags.plus(tag))
            }
    }

    fun removeFilterTag(tag: String) {
        _filterUiState.update {
            it.copy(tags = it.tags.minus(tag))
        }
    }

    fun updateCocktail(cocktail: Cocktail) = viewModelScope.launch {
        updateCocktailUseCase(cocktail)
    }

    fun selectorChanged(clicked: FooterSelector) {
        when (clicked) {
            FooterSelector.ALL_COCKTAILS -> {
                _selectorUiState.update { it.copy(isAllCocktailsSelected = it.isAllCocktailsSelected.not()) }
            }
            FooterSelector.MY_COCKTAILS -> {
                _selectorUiState.update { it.copy(isMyCocktailSelected = it.isMyCocktailSelected.not()) }
            }
            FooterSelector.FAVORITE_COCKTAILS -> {
                _selectorUiState.update { it.copy(isFavoriteSelected = it.isFavoriteSelected.not()) }
            }
        }
    }
}