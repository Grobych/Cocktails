package com.globa.cocktails.ui.cocktaillist

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.domain.FavoriteCocktailsUseCase
import com.globa.cocktails.domain.FilterCocktailsUseCase
import com.globa.cocktails.domain.GetAllReceipesUseCase
import com.globa.cocktails.domain.GetRandomRecipeUseCase
import com.globa.cocktails.domain.SetIsFavoriteUseCase
import com.globa.cocktails.domain.models.RecipePreview
import com.globa.cocktails.utils.contains
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val getAllReceipesUseCase: GetAllReceipesUseCase,
    private val setIsFavoriteUseCase: SetIsFavoriteUseCase,
    private val favoriteCocktailsUseCase: FavoriteCocktailsUseCase,
    private val filterCocktailsUseCase: FilterCocktailsUseCase,
    private val randomCocktailUseCase: GetRandomRecipeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CocktailListUiState>(CocktailListUiState.Loading())
    val uiState = _uiState.asStateFlow()

    private val _filterUiState = MutableStateFlow(CocktailFilterUiState())
    val filterUiState = _filterUiState.asStateFlow()

    private val _selectorUiState = MutableStateFlow(CocktailSelectorUiState())
    val selectorUiState = _selectorUiState.asStateFlow()

    private fun initCocktailList() = viewModelScope.launch {
        getAllReceipesUseCase()
            .catch { trowable ->
                _uiState.update {
                    if (trowable.message != null) CocktailListUiState.Error(trowable.message!!)
                    else CocktailListUiState.Error(trowable.toString())
                }
            }
            .combine(selectorUiState) { cocktails: List<RecipePreview>, state: CocktailSelectorUiState ->
                if (state.isFavoriteSelected) favoriteCocktailsUseCase(cocktails)
                else if (state.isMyCocktailSelected) emptyList() // TODO: myCocktailsUseCase
                    else cocktails
            }
            .combine(filterUiState) { cocktails: List<RecipePreview>, filter: CocktailFilterUiState ->
                if (filter.tags.isEmpty() && filter.line.text.isEmpty()) cocktails
                else filterCocktailsUseCase(cocktails,filter.expandTags())
            }
            .collect { list ->
                _uiState.update {
                    CocktailListUiState.Done(list)
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
    fun getRandomReceipeId() =
        randomCocktailUseCase((uiState.value as CocktailListUiState.Done).list.map { it.id })

    fun changeIsFavorite(id: Int, value: Boolean) = viewModelScope.launch {
        setIsFavoriteUseCase(id, value)
    }

    fun selectorChanged(clicked: FooterSelector) {
        when (clicked) {
            FooterSelector.ALL_COCKTAILS -> {
                _selectorUiState.update {
                    it.copy(
                        isAllCocktailsSelected = true,
                        isMyCocktailSelected = false,
                        isFavoriteSelected = false
                    )
                }
            }
            FooterSelector.MY_COCKTAILS -> {
                _selectorUiState.update {
                    it.copy(
                        isAllCocktailsSelected = false,
                        isMyCocktailSelected = true,
                        isFavoriteSelected = false
                    )
                }
            }
            FooterSelector.FAVORITE_COCKTAILS -> {
                _selectorUiState.update {
                    it.copy(
                        isAllCocktailsSelected = false,
                        isMyCocktailSelected = false,
                        isFavoriteSelected = true
                    )
                }
            }
        }
    }
}