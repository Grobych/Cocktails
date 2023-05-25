package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.asDBModel
import com.globa.cocktails.datalayer.models.asDomainModel
import com.globa.cocktails.datalayer.storage.CocktailFileDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


interface CocktailRepository {
    suspend fun getCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktail(id: String): Flow<Cocktail>
    suspend fun updateCocktail(cocktail: Cocktail)
    suspend fun saveCocktail(cocktail: Cocktail)
}

@Singleton
class CocktailRepositoryImpl @Inject constructor (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailFileDataSource: CocktailFileDataSource
): CocktailRepository {


    override suspend fun getCocktails(): Flow<List<Cocktail>> {
        return cocktailLocalDataSource.getCocktails()
            .map {
                    it
                        .asDomainModel()
                        .ifEmpty {
                            cocktailLocalDataSource.putCocktails(cocktailFileDataSource.getCocktails().asDBModel())
                            cocktailFileDataSource.getCocktails().asDomainModel()
                    }
            }
    }

    override suspend fun getCocktail(id: String): Flow<Cocktail> {
        return cocktailLocalDataSource.getCocktailById(id).map { it.asDomainModel() }
    }

    override suspend fun updateCocktail(cocktail: Cocktail) {
        cocktailLocalDataSource.updateCocktail(cocktail.asDBModel())
    }

    override suspend fun saveCocktail(cocktail: Cocktail) {
        cocktailLocalDataSource.putCocktail(cocktail.asDBModel())
    }
}