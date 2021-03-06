package com.globa.cocktails.datalayer.network

import com.globa.cocktails.datalayer.models.CocktailAPIModel
import com.globa.cocktails.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailNetworkDataSource @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val cocktailNetworkService : CocktailNetworkService) {

    suspend fun getCocktails() : List<CocktailAPIModel> =
        withContext(coroutineDispatcher){
            cocktailNetworkService.getDrinks().filter { it.drinkNumber != 0 }
        }

}