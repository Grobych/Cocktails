package com.globa.cocktails.domain.editlog

import com.globa.cocktails.data.api.EditRecipeLog
import javax.inject.Inject

class AddEditLogUseCase @Inject constructor(
    private val repository: com.globa.cocktails.data.api.EditLogRepository
) {
    suspend operator fun invoke(log: EditRecipeLog) = repository.addLog(log)
}