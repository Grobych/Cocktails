package com.globa.cocktails.data.internal.network

import com.globa.cocktails.data.internal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailNetworkDataSource @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val cocktailNetworkService : CocktailNetworkService
) {

    suspend fun getCocktails() : List<CocktailAPIModel> =
        withContext(coroutineDispatcher){
            cocktailNetworkService.getDrinks()
        }

}