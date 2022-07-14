package com.globa.cocktails.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globa.cocktails.datalayer.models.Cocktail

class CocktailViewModel(cocktail: Cocktail) : ViewModel() {

    val cocktail = MutableLiveData(cocktail)

    class Factory(private val cocktail: Cocktail) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CocktailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CocktailViewModel(cocktail) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}