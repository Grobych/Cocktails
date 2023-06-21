package com.globa.cocktails.data.editlog.internal

import com.globa.cocktails.data.editlog.api.EditLogRepository
import com.globa.cocktails.data.editlog.api.EditRecipeLog
import com.globa.cocktails.data.editlog.api.toDBModel
import com.globa.cocktails.data.editlog.api.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class EditLogRepositoryImpl @Inject constructor(
    private val editRecipeLogDataSource: EditRecipeLogDataSource
): EditLogRepository {
    override suspend fun getLogs(): Flow<List<EditRecipeLog>> = editRecipeLogDataSource.getLogs().map { list -> list.map { it.toDomainModel() } }

    override suspend fun addLog(log: EditRecipeLog) {
        editRecipeLogDataSource.addLog(log.toDBModel())
    }
}