package com.globa.cocktails.data.cocktail.internal

import com.globa.cocktails.data.cocktail.api.Cocktail
import com.globa.cocktails.data.cocktail.api.CocktailRepository
import com.globa.cocktails.data.cocktail.api.asDBModel
import com.globa.cocktails.database.api.model.CocktailDBModel
import com.globa.cocktails.filestorage.api.CocktailAPIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class CocktailRepositoryImpl @Inject constructor (
    private val cocktailLocalDataSource: CocktailLocalDataSource,
    private val cocktailFileDataSource: CocktailFileDataSource
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

    override suspend fun loadRecipes(excluded: List<String>) {
        cocktailFileDataSource
            .getCocktails()
            .map { list ->
                list.filter {
                    !excluded.contains(it.drinkName)
                }
            }
            .collect {
                cocktailLocalDataSource.putCocktails(it.asDBModel())
            }
    }
}

fun List<CocktailAPIModel>.asDBModel() = map {
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