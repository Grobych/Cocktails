package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.CocktailLocalDataSource
import com.globa.cocktails.datalayer.models.CocktailAPIModel
import com.globa.cocktails.datalayer.models.asDBModel
import com.globa.cocktails.datalayer.models.asDomainModel
import com.globa.cocktails.datalayer.storage.CocktailFileDataSource
import com.globa.cocktails.domain.Cocktail
import com.globa.cocktails.domain.asDBModel
import com.globa.cocktails.domain.editlog.EditRecipeLog
import com.globa.cocktails.domain.editlog.contains
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


interface CocktailRepository {
    suspend fun getCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktail(id: Int): Flow<Cocktail>
    suspend fun updateCocktail(cocktail: Cocktail)
    suspend fun saveCocktail(cocktail: Cocktail)
    suspend fun loadRecipes()
}

@Singleton
class CocktailRepositoryImpl @Inject constructor (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailFileDataSource: CocktailFileDataSource,
    private val editLogRepository: EditLogRepository
): CocktailRepository {


    override suspend fun getCocktails(): Flow<List<Cocktail>> =
        cocktailLocalDataSource
            .getCocktails()
            .map {
                list -> list.map {
                    it.asDomainModel()
                }
            }

    override suspend fun getCocktail(id: Int): Flow<Cocktail> {
        return cocktailLocalDataSource.getCocktailById(id).map { it.asDomainModel() }
    }

    override suspend fun updateCocktail(cocktail: Cocktail) {
        cocktailLocalDataSource.updateCocktail(cocktail.asDBModel())
    }

    override suspend fun saveCocktail(cocktail: Cocktail) {
        cocktailLocalDataSource.putCocktail(cocktail.asDBModel())
    }

    override suspend fun loadRecipes() {
        cocktailFileDataSource
            .getCocktails()
            .combine(editLogRepository.getLogs()) { recipes: List<CocktailAPIModel>, edites: List<EditRecipeLog> ->
                recipes.filter { recipe ->
                    !edites.contains(recipe.drinkName)
                    // edge case: if cocktail has been edided, but later was removed from cocktails
                }
            }
            .collect {
                cocktailLocalDataSource.putCocktails(it.asDBModel())
            }
    }
}