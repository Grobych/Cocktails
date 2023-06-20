package com.globa.cocktails.data.api

import kotlinx.coroutines.flow.Flow

interface FavoritedCocktailRepository {
    suspend fun getFavorites(): Flow<List<Favorited>>
    suspend fun add(favorited: Favorited)
    suspend fun remove(favorited: Favorited)
}