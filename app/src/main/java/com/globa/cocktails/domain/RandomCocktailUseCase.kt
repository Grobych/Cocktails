package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class RandomCocktailUseCase @Inject constructor(
    private val repository: CocktailRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() : Cocktail = withContext(coroutineDispatcher){
            val list = repository.getCocktails()
            val random = Random(Calendar.getInstance().time.time).nextInt(list.lastIndex)
            return@withContext list[random]
        }
}