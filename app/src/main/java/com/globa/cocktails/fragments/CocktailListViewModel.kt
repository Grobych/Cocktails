package com.globa.cocktails.fragments

import androidx.lifecycle.*
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
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