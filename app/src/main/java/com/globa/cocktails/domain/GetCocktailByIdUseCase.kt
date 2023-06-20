package com.globa.cocktails.domain

import javax.inject.Inject

class GetCocktailByIdUseCase @Inject constructor(
    private val repository: com.globa.cocktails.data.api.CocktailRepository
) { //TODO: catch not such id error?
    suspend operator fun invoke(id: Int) = repository.getCocktail(id)
}