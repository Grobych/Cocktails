package com.globa.cocktails.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.globa.cocktails.database.getDatabase
import com.globa.cocktails.models.Cocktail
import com.globa.cocktails.repository.CocktailRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class CocktailListViewModel(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val tag = this.javaClass.simpleName

    var cocktails = MutableLiveData<List<Cocktail>>()


    fun loadCocktails(){
        viewModelScope.launch {
            cocktails.postValue(cocktailRepository.getCocktails())
        }
    }



    class Factory(private val repository: CocktailRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CocktailListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CocktailListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}