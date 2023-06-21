package com.globa.cocktails.domain

import com.globa.cocktails.data.cocktail.api.CocktailRepository
import com.globa.cocktails.data.editlog.api.EditLogRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoadCocktailsUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val editLogRepository: EditLogRepository
) {
    suspend operator fun invoke() {
        val excluded = editLogRepository.getLogs().first().map { it.name }
        cocktailRepository.loadRecipes(excluded)
    }
}