package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import kotlin.random.Random

class RandomCocktailUseCase {
    operator fun invoke(list: List<Cocktail>): String {
        if (list.isNotEmpty()) return list[Random.Default.nextInt(list.lastIndex + 1)].id
        else throw java.lang.Exception("No items to get random!")
    }
}