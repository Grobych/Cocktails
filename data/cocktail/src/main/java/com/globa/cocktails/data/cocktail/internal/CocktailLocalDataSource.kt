package com.globa.cocktails.data.cocktail.internal

import com.globa.cocktails.common.IoDispatcher
import com.globa.cocktails.database.api.CocktailDatabase
import com.globa.cocktails.database.api.model.CocktailDBModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class CocktailLocalDataSource @Inject constructor(
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