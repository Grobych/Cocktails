package com.globa.cocktails.ui.cocktaillist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.domain.favorites.Favorited
import com.globa.cocktails.domain.favorites.GetFavoritesUseCase
import com.globa.cocktails.domain.favorites.IsFavoriteCocktailUseCase
import com.globa.cocktails.domain.favorites.SetIsFavoriteUseCase
import com.globa.cocktails.domain.getrandom.GetRandomRecipeUseCase
import com.globa.cocktails.domain.getreceipes.FilterCocktailsUseCase
import com.globa.cocktails.domain.getreceipes.GetAllReceipesUseCase
import com.globa.cocktails.domain.getreceipes.GetFavoriteCocktailsUseCase
import com.globa.cocktails.domain.getreceipes.RecipePreview
import com.globa.cocktails.utils.contains
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val getAllReceipesUseCase: GetAllReceipesUseCase,
    private val setIsFavoriteUseCase: SetIsFavoriteUseCase,
    private val favoriteCocktailsUseCase: GetFavoriteCocktailsUseCase,
    private val filterCocktailsUseCase: FilterCocktailsUseCase,
    private val randomCocktailUseCase: GetRandomRecipeUseCase,
    private val isFavoriteUseCase: IsFavoriteCocktailUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
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
            .combine(getFavoritesUseCase()) { cocktails: List<RecipePreview>, _: List<Favorited> ->
                cocktails.map { it.copy(isFavorite = isFavoriteUseCase(it.name).first())}
            }
            .combine(selectorUiState) { cocktails: List<RecipePreview>, state: CocktailSelectorUiState ->
                if (state.isFavoriteSelected) favoriteCocktailsUseCase(cocktails)
                else if (state.isMyCocktailSelected) emptyList() // TODO: myCocktailsUseCase
                    else cocktails
            }
            .combine(filterUiState) { cocktails: List<RecipePreview>, filter: CocktailFilterUiState ->
                if (filter.tags.isEmpty() && filter.line.isEmpty()) cocktails
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

    fun updateFilterLine(line: String) {
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

    fun changeIsFavorite(name: String, value: Boolean) = viewModelScope.launch {
        setIsFavoriteUseCase(name, value)
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