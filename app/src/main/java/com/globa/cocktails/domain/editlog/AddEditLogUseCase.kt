package com.globa.cocktails.domain.editlog

import com.globa.cocktails.datalayer.repository.EditLogRepository
import javax.inject.Inject

class AddEditLogUseCase @Inject constructor(
    private val repository: EditLogRepository
) {
    suspend operator fun invoke(log: EditRecipeLog) = repository.addLog(log)
}