package com.globa.cocktails.domain.repo

import com.globa.cocktails.domain.editlog.EditRecipeLog
import kotlinx.coroutines.flow.Flow

interface EditLogRepository {
    suspend fun getLogs(): Flow<List<EditRecipeLog>>
    suspend fun addLog(log: EditRecipeLog)
}