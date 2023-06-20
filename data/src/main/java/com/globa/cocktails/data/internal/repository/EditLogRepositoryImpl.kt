package com.globa.cocktails.data.internal.repository

import com.globa.cocktails.data.api.EditRecipeLog
import com.globa.cocktails.data.api.toDBModel
import com.globa.cocktails.data.api.toDomainModel
import com.globa.cocktails.data.internal.database.editlog.EditRecipeLogDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EditLogRepositoryImpl @Inject constructor(
    private val editRecipeLogDataSource: EditRecipeLogDataSource
): com.globa.cocktails.data.api.EditLogRepository {
    override suspend fun getLogs(): Flow<List<EditRecipeLog>> = editRecipeLogDataSource.getLogs().map { list -> list.map { it.toDomainModel() } }

    override suspend fun addLog(log: EditRecipeLog) {
        editRecipeLogDataSource.addLog(log.toDBModel())
    }
}