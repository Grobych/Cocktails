package com.globa.cocktails.datalayer.repository

import com.globa.cocktails.datalayer.database.editlog.EditRecipeLogDataSource
import com.globa.cocktails.domain.editlog.EditRecipeLog
import com.globa.cocktails.domain.editlog.toDBModel
import com.globa.cocktails.domain.editlog.toDomainModel
import com.globa.cocktails.domain.repo.EditLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EditLogRepositoryImpl @Inject constructor(
    private val editRecipeLogDataSource: EditRecipeLogDataSource
): EditLogRepository {
    override suspend fun getLogs(): Flow<List<EditRecipeLog>> = editRecipeLogDataSource.getLogs().map { list -> list.map { it.toDomainModel() } }

    override suspend fun addLog(log: EditRecipeLog) {
        editRecipeLogDataSource.addLog(log.toDBModel())
    }
}