package com.globa.cocktails.domain.getreceipes

import javax.inject.Inject

class FilterCocktailsUseCase @Inject constructor(){
    operator fun invoke(cocktails: List<RecipePreview>, tags: List<String>): List<RecipePreview> =
        cocktails.filter { it.contains(tags) }

    fun RecipePreview.contains(tags: List<String>): Boolean {
        val cocktailTagsLine = this.createTagLine()
        tags.forEach { tag ->
            if (!cocktailTagsLine.contains(tag, ignoreCase = true)) return false
        }
        return true
    }
}

fun RecipePreview.createTagLine(): String {
    val res = StringBuilder()
        .append("$name ")
    tags.forEach {
        res.append("$it ")
    }
    return res.toString()
}

