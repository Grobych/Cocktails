package com.globa.cocktails.network

import com.globa.cocktails.models.Cocktail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CocktailNetworkDataSource(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val cocktailNetworkService : CocktailNetworkService) {

    suspend fun getCocktails() : List<Cocktail> =
        withContext(coroutineDispatcher){
            cocktailNetworkService.instance.getDrinks()
        }

}