package com.globa.cocktails.domain.editrecipe

import com.globa.cocktails.data.editlog.api.EditLogRepository
import com.globa.cocktails.data.editlog.api.EditRecipeLog
import javax.inject.Inject

class AddEditLogUseCase @Inject constructor(
    private val repository: EditLogRepository
) {
    suspend operator fun invoke(log: EditRecipeLog) = repository.addLog(log)
}