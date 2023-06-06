package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.models.ReceipePreview
import com.globa.cocktails.domain.models.toReceipePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllReceipesUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(): Flow<List<ReceipePreview>> =
        repository.getCocktails().map {
                list -> list.map {
                    it.toReceipePreview()
                }
        }
}