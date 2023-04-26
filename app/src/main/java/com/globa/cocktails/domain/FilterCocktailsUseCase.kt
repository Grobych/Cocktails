package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.models.createTagLine

object FilterCocktailsUseCase {
    fun List<Cocktail>.filterByTags(tags: List<String>) : List<Cocktail> =
        this.filter { it.contains(tags) }
}

fun Cocktail.contains(tags: List<String>): Boolean {
    val cocktailTagsLine = this.createTagLine()
    tags.forEach { tag ->
        if (!cocktailTagsLine.contains(tag, ignoreCase = true)) return false
    }
    return true
}