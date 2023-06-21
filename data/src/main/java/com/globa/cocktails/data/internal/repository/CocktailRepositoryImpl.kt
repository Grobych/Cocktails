package com.globa.cocktails.data.internal.repository

import com.globa.cocktails.data.api.Cocktail
import com.globa.cocktails.data.api.CocktailRepository
import com.globa.cocktails.data.api.EditLogRepository
import com.globa.cocktails.data.api.EditRecipeLog
import com.globa.cocktails.data.api.asDBModel
import com.globa.cocktails.data.api.contains
import com.globa.cocktails.data.internal.storage.CocktailFileDataSource
import com.globa.cocktails.database.api.model.CocktailDBModel
import com.globa.cocktails.database.internal.cocktail.CocktailLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


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
            .combine(editLogRepository.getLogs()) { recipes: List<com.globa.cocktails.filestorage.api.CocktailAPIModel>, edites: List<EditRecipeLog> ->
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

fun List<com.globa.cocktails.filestorage.api.CocktailAPIModel>.asDBModel() = map {
    CocktailDBModel(
        id = 0,
        drinkName = it.drinkName,
        alcohol = it.alcohol,
        drinkCategory = it.drinkCategory,
        imageURL = it.imageURL,
        drinkGlass = it.drinkGlass,
        ingredients = it.ingredients,
        measures = it.measures,
        instructions = it.instructions
    )
}

fun List<CocktailDBModel>.asDomainModel() = map {
    Cocktail(
        id = it.id,
        drinkName = it.drinkName,
        alcohol = it.alcohol,
        drinkCategory = it.drinkCategory,
        imageURL = it.imageURL,
        drinkGlass = it.drinkGlass,
        ingredients = it.ingredients,
        measures = it.measures,
        instructions = it.instructions
    )
}

fun CocktailDBModel.asDomainModel() =
    Cocktail(
        id = id,
        drinkName = drinkName,
        alcohol = alcohol,
        drinkCategory = drinkCategory,
        imageURL = imageURL,
        drinkGlass = drinkGlass,
        ingredients = ingredients,
        measures = measures,
        instructions = instructions
    )