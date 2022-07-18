package com.globa.cocktails.domain

import android.util.Log
import android.util.TimeUtils
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

class RandomCocktailUseCase(
    private val repository: CocktailRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() : Cocktail = withContext(coroutineDispatcher){
            val list = repository.getCocktails()
            val random = Random(Calendar.getInstance().time.time).nextInt(list.lastIndex)
            return@withContext list[random]
        }
}