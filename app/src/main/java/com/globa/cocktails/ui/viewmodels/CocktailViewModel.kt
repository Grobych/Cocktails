package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CocktailViewModel @AssistedInject constructor(
    @Assisted("cocktailId") cocktailId: String,
    repository: CocktailRepository
) : ViewModel() {

    private val _cocktail = MutableStateFlow(Cocktail())
    val cocktail = _cocktail.asStateFlow()

    init {
        viewModelScope.launch {
            val cocktail = repository.getCocktails().find { it.id == cocktailId }
            cocktail?.let { _cocktail.value = it } // TODO
        }
    }
}

class CocktailViewModelFactory @AssistedInject constructor(
    @Assisted("cocktailId") private val cocktailId: String,
    private val repository: CocktailRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CocktailViewModel::class.java)) {
            return CocktailViewModel(cocktailId, repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("cocktailId") cocktailId: String): CocktailViewModelFactory
    }
}
