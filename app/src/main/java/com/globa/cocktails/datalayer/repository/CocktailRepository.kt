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
    suspend fun getFavorites(): List<Cocktail>

    suspend fun getCocktail(id: String): Cocktail
    suspend fun updateCocktail(cocktail: Cocktail)
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

    override suspend fun getFavorites(): List<Cocktail> {
        return cocktailLocalDataSource.getFavoriteCocktails().asDomainModel()
    }

    override suspend fun getCocktail(id: String): Cocktail {
        return  cocktailLocalDataSource.getCocktailById(id).asDomainModel()
    }

    override suspend fun updateCocktail(cocktail: Cocktail) {
        cocktailLocalDataSource.updateCocktail(cocktail.asDBModel())
    }
}