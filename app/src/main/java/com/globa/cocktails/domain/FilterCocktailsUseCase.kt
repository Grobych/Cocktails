package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.CocktailFilter
import com.globa.cocktails.datalayer.repository.CocktailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterCocktailsUseCase(
    private val cocktailRepository: CocktailRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default) {

    suspend operator fun invoke(filter: CocktailFilter) : List<Cocktail> = withContext(coroutineDispatcher){
        return@withContext cocktailRepository.getCocktails().filter {
            filter.name.ifEmpty { true }
            it.drinkName.contains(filter.name, ignoreCase = true) }
    }

}