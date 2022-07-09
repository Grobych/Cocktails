package com.globa.cocktails.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.globa.cocktails.database.getDatabase
import com.globa.cocktails.repository.Repository
import kotlinx.coroutines.launch

class CocktailListViewModel(application: Application) : ViewModel() {
    private val tag = this.javaClass.simpleName

    private val repository = Repository(getDatabase(application.applicationContext))
    val cocktails = repository.cocktails

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
            try {
                repository.refreshCocktailsFromNet()
            } catch (error : Exception){
                Log.e(tag, "${error.message}")
            }
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CocktailListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CocktailListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}