package com.globa.cocktails.datalayer.database.cocktail

import com.globa.cocktails.datalayer.database.CocktailDatabase
import com.globa.cocktails.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailLocalDataSource @Inject constructor(
    private val db : CocktailDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getCocktails() = withContext(coroutineDispatcher) {db.cocktailDao.getCocktails()}

    suspend fun putCocktails(cocktails : List<CocktailDBModel>) =
        withContext(coroutineDispatcher){
            db.cocktailDao.insertAll(cocktails)
        }

    suspend fun putCocktail(cocktail: CocktailDBModel) =
        withContext(coroutineDispatcher) {
            db.cocktailDao.insert(cocktail)
        }

    suspend fun updateCocktail(cocktail: CocktailDBModel) =
        withContext(coroutineDispatcher) {
            db.cocktailDao.update(cocktail)
        }

    suspend fun getCocktailById(id: Int) = withContext(coroutineDispatcher) {db.cocktailDao.getCocktailById(id)}

}