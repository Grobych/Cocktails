package com.globa.cocktails.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.globa.cocktails.database.getDatabase
import com.globa.cocktails.repository.Repository
import kotlinx.coroutines.launch

class CocktailListViewModel(application: Application) : ViewModel() {
    private val tag = this.javaClass.simpleName

    private val repository = Repository(getDatabase(application.applicationContext))
    var cocktails = repository.cocktails
    var filter = MutableLiveData("%")
    fun setFilter(newFilter: String) {
        val f = when {
            newFilter.isEmpty() -> "%"
            else -> "%$newFilter%"
        }
        filter.postValue(f)
    }

    init {
        refreshDataFromRepository()
        cocktails = Transformations.switchMap(filter) { filter ->
            repository.refreshCocktailsWithFilter(filter)
        }
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