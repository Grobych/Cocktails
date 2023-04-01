package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: CocktailRepository
) : ViewModel() {

    private val _cocktail = MutableStateFlow(Cocktail())
    val cocktail = _cocktail.asStateFlow()

    init {
        viewModelScope.launch {
            val cocktailId = savedStateHandle.get<String>("cocktailId")
            val cocktail = repository.getCocktails().find { it.id == cocktailId }
            cocktail?.let { _cocktail.value = it } // TODO
        }
    }
}