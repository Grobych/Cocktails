package com.globa.cocktails.ui.cocktaillist

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.FilterCocktailsUseCase.filterByTags
import com.globa.cocktails.ui.UiStateStatus
import com.globa.cocktails.utils.contains
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailListUiState())
    val uiState : StateFlow<CocktailListUiState> = _uiState.asStateFlow()

    private val _filterUiState = MutableStateFlow(CocktailFilterUiState())
    val filterUiState = _filterUiState.asStateFlow()

    private val cocktails =
        cocktailRepository
            .getCocktails()
            .combine(filterUiState) { cocktails: List<Cocktail>, filter: CocktailFilterUiState ->
                if (filter.tags.isEmpty() && filter.line.text.isEmpty()) cocktails
                else cocktails.filterByTags(filter.expandTags())
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private fun initCocktailList() {
        cocktails.onEach {
            _uiState.update { uiState ->
                uiState.copy(
                    status = UiStateStatus.DONE,
                    cocktailList = it
                )
            }
        }.launchIn(viewModelScope)
    }

    init {
        initCocktailList()
    }

    fun getRandomCocktail() : String {
        val list = cocktails.value
        return list[Random.Default.nextInt(list.lastIndex + 1)].id
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

    fun updateCocktail(cocktail: Cocktail) {
        viewModelScope.launch {
            cocktailRepository.updateCocktail(cocktail)
        }
    }
}