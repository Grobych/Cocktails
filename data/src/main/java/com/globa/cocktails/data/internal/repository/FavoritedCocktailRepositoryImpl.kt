package com.globa.cocktails.data.internal.repository

import com.globa.cocktails.data.api.Favorited
import com.globa.cocktails.data.internal.database.favorite.FavoritedCocktailDataSource
import com.globa.cocktails.data.api.asDBModel
import com.globa.cocktails.data.api.asDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritedCocktailRepositoryImpl @Inject constructor(
    private val favoritedCocktailDataSource: FavoritedCocktailDataSource
): com.globa.cocktails.data.api.FavoritedCocktailRepository {
    override suspend fun getFavorites(): Flow<List<Favorited>> =
        favoritedCocktailDataSource.getFavorites().map { list -> list.map { it.asDomainModel() } }

    override suspend fun add(favorited: Favorited) =
        favoritedCocktailDataSource.addFavorited(favorited.asDBModel())

    override suspend fun remove(favorited: Favorited) =
        favoritedCocktailDataSource.removeFavorited(favorited.asDBModel())
}