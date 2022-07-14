package com.globa.cocktails.datalayer.database

import com.globa.cocktails.datalayer.models.CocktailDBModel
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
    suspend fun putCocktails(cocktails : List<CocktailDBModel>) =
        withContext(coroutineDispatcher){
            db.cocktailDao.insertAll(cocktails)
        }
}