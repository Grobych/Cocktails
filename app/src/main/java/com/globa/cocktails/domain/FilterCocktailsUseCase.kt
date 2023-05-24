package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.createTagLine
import javax.inject.Inject

class FilterCocktailsUseCase @Inject constructor(){
    operator fun invoke(cocktails: List<Cocktail>, tags: List<String>): List<Cocktail> =
        cocktails.filter { it.contains(tags) }

    fun Cocktail.contains(tags: List<String>): Boolean {
        val cocktailTagsLine = this.createTagLine()
        tags.forEach { tag ->
            if (!cocktailTagsLine.contains(tag, ignoreCase = true)) return false
        }
        return true
    }
}

