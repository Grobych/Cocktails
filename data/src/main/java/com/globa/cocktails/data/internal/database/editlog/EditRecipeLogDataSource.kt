package com.globa.cocktails.data.internal.database.editlog

import com.globa.cocktails.data.internal.database.CocktailDatabase
import com.globa.cocktails.data.internal.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditRecipeLogDataSource @Inject constructor(
    private val db: CocktailDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getLogs() = withContext(coroutineDispatcher) {db.editLogDao.getEditLogs()}

    suspend fun addLog(log: EditRecipeLogDBModel) = withContext(coroutineDispatcher) {db.editLogDao.addEditRecipeLog(log)}
}