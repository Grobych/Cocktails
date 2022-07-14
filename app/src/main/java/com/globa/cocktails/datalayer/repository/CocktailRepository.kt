package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.asDomainModel
import com.globa.cocktails.datalayer.network.CocktailNetworkDataSource

class CocktailRepository (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailNetworkDataSource: CocktailNetworkDataSource
        ) {

    private var cocktails : List<Cocktail> = emptyList()

    suspend fun getCocktails() : List<Cocktail> {
        if (cocktails.isEmpty()){
            cocktails = cocktailLocalDataSource.getCocktails().ifEmpty {
                val cocktailFromNet = cocktailNetworkDataSource.getCocktails()
                cocktailLocalDataSource.putCocktails(cocktailFromNet.asDomainModel())
                cocktailFromNet.asDomainModel()
            }
        }
        return cocktails
    }
}