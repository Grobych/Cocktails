package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globa.cocktails.datalayer.models.Cocktail
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CocktailViewModel @AssistedInject constructor(
    @Assisted("cocktail") cocktail: Cocktail
) : ViewModel() {

    private val _cocktail = MutableStateFlow(cocktail)
    val cocktail = _cocktail.asStateFlow()
}

class CocktailViewModelFactory @AssistedInject constructor(
    @Assisted("cocktail") private val cocktail: Cocktail
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CocktailViewModel::class.java)) {
            return CocktailViewModel(cocktail) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("cocktail") cocktail: Cocktail): CocktailViewModelFactory
    }
}
