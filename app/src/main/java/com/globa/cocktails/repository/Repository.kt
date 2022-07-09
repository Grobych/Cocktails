package com.globa.cocktails.repository

import androidx.lifecycle.LiveData
import com.globa.cocktails.database.CocktailDatabase
import com.globa.cocktails.models.Cocktail
import com.globa.cocktails.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository (private val database: CocktailDatabase) {

    val cocktails : LiveData<List<Cocktail>> = database.cocktailDao.getCocktails()

    suspend fun refreshCocktailsFromNet(){
        withContext(Dispatchers.IO){
            database.cocktailDao.insertAll(Network.instance.getDrinks())
        }
    }
}