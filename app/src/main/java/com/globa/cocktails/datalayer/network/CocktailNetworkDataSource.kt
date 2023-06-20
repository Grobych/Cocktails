package com.globa.cocktails.datalayer.network

import com.globa.cocktails.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailNetworkDataSource @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val cocktailNetworkService : CocktailNetworkService) {

    suspend fun getCocktails() : List<CocktailAPIModel> =
        withContext(coroutineDispatcher){
            cocktailNetworkService.getDrinks()
        }

}