package com.globa.cocktails.data.cocktail.internal

import com.globa.cocktails.common.IoDispatcher
import com.globa.cocktails.filestorage.api.CocktailAPIModel
import com.globa.cocktails.filestorage.api.FileStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class CocktailFileDataSource @Inject constructor(
    private val fileStorage: FileStorage,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getCocktails(): Flow<List<CocktailAPIModel>> = withContext(coroutineDispatcher) {
        fileStorage.getCocktails()
    }
}