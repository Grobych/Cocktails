package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.asDBModel
import com.globa.cocktails.datalayer.models.asDomainModel
import com.globa.cocktails.datalayer.storage.CocktailFileDataSource
import javax.inject.Inject
import javax.inject.Singleton


interface CocktailRepository {
    suspend fun getCocktails(): List<Cocktail>
}

@Singleton
class CocktailRepositoryImpl @Inject constructor (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailFileDataSource: CocktailFileDataSource
): CocktailRepository {

    private var cocktails : List<Cocktail> = emptyList()

    override suspend fun getCocktails() : List<Cocktail> {
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