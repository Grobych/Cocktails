package com.globa.cocktails.domain.repo

import com.globa.cocktails.domain.favorites.Favorited
import kotlinx.coroutines.flow.Flow

interface FavoritedCocktailRepository {
    suspend fun getFavorites(): Flow<List<Favorited>>
    suspend fun add(favorited: Favorited)
    suspend fun remove(favorited: Favorited)
}