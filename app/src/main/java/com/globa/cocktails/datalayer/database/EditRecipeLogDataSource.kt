package com.globa.cocktails.datalayer.database

import com.globa.cocktails.datalayer.models.EditRecipeLogDBModel
import com.globa.cocktails.di.modules.IoDispatcher
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