package com.globa.cocktails.data.api

import kotlinx.coroutines.flow.Flow

interface EditLogRepository {
    suspend fun getLogs(): Flow<List<EditRecipeLog>>
    suspend fun addLog(log: EditRecipeLog)
}