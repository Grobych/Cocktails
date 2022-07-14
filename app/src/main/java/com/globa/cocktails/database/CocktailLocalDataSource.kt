package com.globa.cocktails.database

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
}