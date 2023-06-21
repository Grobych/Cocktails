package com.globa.cocktails.data.api

import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktail(id: Int): Flow<Cocktail>
    suspend fun updateCocktail(cocktail: Cocktail)
    suspend fun saveCocktail(cocktail: Cocktail)
    suspend fun loadRecipes(excluded: List<String>)
}