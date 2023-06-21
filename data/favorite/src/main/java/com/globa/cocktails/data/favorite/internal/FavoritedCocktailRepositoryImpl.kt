package com.globa.cocktails.data.favorite.internal

import com.globa.cocktails.data.favorite.api.Favorited
import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
import com.globa.cocktails.data.favorite.api.asDBModel
import com.globa.cocktails.data.favorite.api.asDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FavoritedCocktailRepositoryImpl @Inject constructor(
    private val favoritedCocktailDataSource: FavoritedCocktailDataSource
): FavoritedCocktailRepository {
    override suspend fun getFavorites(): Flow<List<Favorited>> =
        favoritedCocktailDataSource.getFavorites().map { list -> list.map { it.asDomainModel() } }

    override suspend fun add(favorited: Favorited) =
        favoritedCocktailDataSource.addFavorited(favorited.asDBModel())

    override suspend fun remove(favorited: Favorited) =
        favoritedCocktailDataSource.removeFavorited(favorited.asDBModel())
}