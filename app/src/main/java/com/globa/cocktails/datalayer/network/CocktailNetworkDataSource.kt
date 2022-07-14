package com.globa.cocktails.datalayer.network

import com.globa.cocktails.datalayer.models.Cocktail
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