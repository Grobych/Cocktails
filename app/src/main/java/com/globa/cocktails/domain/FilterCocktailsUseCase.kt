package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.CocktailFilter
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterCocktailsUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default) {

    suspend operator fun invoke(filter: CocktailFilter) : List<Cocktail> = withContext(coroutineDispatcher){
        return@withContext cocktailRepository.getCocktails()
            .filter {
            filter.name.ifEmpty { true }
            it.drinkName.contains(filter.name, ignoreCase = true)
            }
            .filter {
                if (filter.type == "All") true
                else it.drinkCategory == filter.type
            }
            .filter{
                if (filter.hasAlcohol == "All") true
                else it.alcohol == when (filter.hasAlcohol){
                    "Alcoholic" -> true
                    else -> false
                }
            }
    }

}