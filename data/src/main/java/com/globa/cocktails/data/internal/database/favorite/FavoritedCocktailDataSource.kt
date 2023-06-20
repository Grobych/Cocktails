package com.globa.cocktails.data.internal.database.favorite

import com.globa.cocktails.data.internal.database.CocktailDatabase
import com.globa.cocktails.data.internal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritedCocktailDataSource @Inject constructor(
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