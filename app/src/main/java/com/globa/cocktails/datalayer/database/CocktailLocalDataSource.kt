package com.globa.cocktails.datalayer.database

import com.globa.cocktails.datalayer.models.Cocktail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CocktailLocalDataSource(
    private val db : CocktailDatabase,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getCocktails() =
        withContext(coroutineDispatcher){
            db.cocktailDao.getCocktails()
        }
    suspend fun putCocktails(cocktails : List<Cocktail>) =
        withContext(coroutineDispatcher){
            db.cocktailDao.insertAll(cocktails)
        }
}