package com.globa.cocktails.domain.getreceipes

import com.globa.cocktails.datalayer.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllReceipesUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(): Flow<List<RecipePreview>> =
        repository.getCocktails().map {
                list -> list.map {
                    it.toReceipePreview()
                }
        }
}