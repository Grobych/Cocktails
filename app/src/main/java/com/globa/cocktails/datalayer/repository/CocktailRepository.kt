package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.asDBModel
import com.globa.cocktails.datalayer.models.asDomainModel
import com.globa.cocktails.datalayer.storage.CocktailFileDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository @Inject constructor (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailFileDataSource: CocktailFileDataSource
) {

    private var cocktails : List<Cocktail> = emptyList()

    suspend fun getCocktails() : List<Cocktail> {
        if (cocktails.isEmpty()){
            cocktails = cocktailLocalDataSource.getCocktails().asDomainModel().ifEmpty {
                val cocktailFromFile = cocktailFileDataSource.getCocktails()
                cocktailLocalDataSource.putCocktails(cocktailFromFile.asDBModel())
                cocktailFromFile.asDomainModel()
            }
        }
        return cocktails
    }
}