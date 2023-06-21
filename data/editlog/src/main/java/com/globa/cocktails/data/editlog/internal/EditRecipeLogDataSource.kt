package com.globa.cocktails.data.editlog.internal

import com.globa.cocktails.common.IoDispatcher
import com.globa.cocktails.database.api.CocktailDatabase
import com.globa.cocktails.database.api.model.EditRecipeLogDBModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class EditRecipeLogDataSource @Inject constructor(
    private val db: CocktailDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getLogs() = withContext(coroutineDispatcher) {db.editLogDao.getEditLogs()}

    suspend fun addLog(log: EditRecipeLogDBModel) = withContext(coroutineDispatcher) {db.editLogDao.addEditRecipeLog(log)}
}