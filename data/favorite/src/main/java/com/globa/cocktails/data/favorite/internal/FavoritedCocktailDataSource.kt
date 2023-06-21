package com.globa.cocktails.data.favorite.internal

import com.globa.cocktails.common.IoDispatcher
import com.globa.cocktails.database.api.CocktailDatabase
import com.globa.cocktails.database.api.model.FavoritedDBModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FavoritedCocktailDataSource @Inject constructor(
    private val db: CocktailDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getFavorites() = withContext(coroutineDispatcher) { db.favoritedDao.getFavorited() }

    suspend fun addFavorited(favorited: FavoritedDBModel) = withContext(coroutineDispatcher) {
        db.favoritedDao.insert(favorited = favorited)
    }

    suspend fun removeFavorited(favorited: FavoritedDBModel) = withContext(coroutineDispatcher) {
        db.favoritedDao.delete(favorited = favorited)
    }
}