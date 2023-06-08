package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import javax.inject.Inject

class GetCocktailByIdUseCase @Inject constructor(
    private val repository: CocktailRepository
) { //TODO: catch not such id error?
    suspend operator fun invoke(id: Int) = repository.getCocktail(id)
}