package com.globa.cocktails.repository

import com.globa.cocktails.database.CocktailLocalDataSource
import com.globa.cocktails.models.Cocktail
import com.globa.cocktails.network.CocktailNetworkDataSource

class CocktailRepository (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailNetworkDataSource: CocktailNetworkDataSource
        ) {

    private var cocktails : List<Cocktail> = emptyList()

    suspend fun getCocktails() : List<Cocktail> {
        if (cocktails.isEmpty()){
            cocktails = cocktailLocalDataSource.getCocktails().ifEmpty { cocktailNetworkDataSource.getCocktails() }
        }
        return cocktails
    }
}