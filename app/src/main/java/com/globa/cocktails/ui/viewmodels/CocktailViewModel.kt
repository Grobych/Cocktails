package com.globa.cocktails.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globa.cocktails.datalayer.models.Cocktail
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CocktailViewModel @AssistedInject constructor(
    @Assisted("cocktail") cocktail: Cocktail
) : ViewModel() {

    val cocktail = MutableLiveData(cocktail)
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
