package com.globa.cocktails.datalayer.network

import com.globa.cocktails.datalayer.models.CocktailAPIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CocktailNetworkDataSource(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val cocktailNetworkService : CocktailNetworkService) {

    suspend fun getCocktails() : List<CocktailAPIModel> =
        withContext(coroutineDispatcher){
            cocktailNetworkService.instance.getDrinks().filter { it.drinkNumber != 0 }
        }

}