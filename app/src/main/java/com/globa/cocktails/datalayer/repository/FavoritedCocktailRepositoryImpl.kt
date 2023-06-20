package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.favorite.FavoritedCocktailDataSource
import com.globa.cocktails.domain.favorites.Favorited
import com.globa.cocktails.domain.favorites.asDBModel
import com.globa.cocktails.domain.favorites.asDomainModel
import com.globa.cocktails.domain.repo.FavoritedCocktailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritedCocktailRepositoryImpl @Inject constructor(
    private val favoritedCocktailDataSource: FavoritedCocktailDataSource
): FavoritedCocktailRepository {
    override suspend fun getFavorites(): Flow<List<Favorited>> =
        favoritedCocktailDataSource.getFavorites().map { list -> list.map { it.asDomainModel() } }

    override suspend fun add(favorited: Favorited) =
        favoritedCocktailDataSource.addFavorited(favorited.asDBModel())

    override suspend fun remove(favorited: Favorited) =
        favoritedCocktailDataSource.removeFavorited(favorited.asDBModel())
}