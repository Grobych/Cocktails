package com.globa.cocktails.domain.repo

import com.globa.cocktails.domain.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktail(id: Int): Flow<Cocktail>
    suspend fun updateCocktail(cocktail: Cocktail)
    suspend fun saveCocktail(cocktail: Cocktail)
    suspend fun loadRecipes()
}