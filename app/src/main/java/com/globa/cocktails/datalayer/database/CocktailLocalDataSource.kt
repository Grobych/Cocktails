package com.globa.cocktails.datalayer.database

import com.globa.cocktails.datalayer.models.CocktailDBModel
import com.globa.cocktails.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailLocalDataSource @Inject constructor(
    private val db : CocktailDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    fun getCocktails() = db.cocktailDao.getCocktails()

    suspend fun putCocktails(cocktails : List<CocktailDBModel>) =
        withContext(coroutineDispatcher){
            db.cocktailDao.insertAll(cocktails)
        }

    suspend fun updateCocktail(cocktail: CocktailDBModel) =
        withContext(coroutineDispatcher) {
            db.cocktailDao.update(cocktail)
        }

    fun getCocktailById(id: String) = db.cocktailDao.getCocktailById(id)

}