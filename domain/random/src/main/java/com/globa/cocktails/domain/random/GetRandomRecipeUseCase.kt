package com.globa.cocktails.domain.random

import javax.inject.Inject
import kotlin.random.Random

class GetRandomRecipeUseCase @Inject constructor(){
    operator fun invoke(list: List<Int>): GetRandomResult {
        return if (list.isNotEmpty())
            GetRandomResult.Success(list[Random.nextInt(list.lastIndex + 1)])
        else GetRandomResult.Error("No items to get random!")
    }
}