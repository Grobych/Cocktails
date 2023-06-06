package com.globa.cocktails.domain

import com.globa.cocktails.domain.models.ReceipePreview
import javax.inject.Inject

class FilterCocktailsUseCase @Inject constructor(){
    operator fun invoke(cocktails: List<ReceipePreview>, tags: List<String>): List<ReceipePreview> =
        cocktails.filter { it.contains(tags) }

    fun ReceipePreview.contains(tags: List<String>): Boolean {
        val cocktailTagsLine = this.createTagLine()
        tags.forEach { tag ->
            if (!cocktailTagsLine.contains(tag, ignoreCase = true)) return false
        }
        return true
    }
}

fun ReceipePreview.createTagLine(): String {
    val res = StringBuilder()
        .append("$name ")
    tags.forEach {
        res.append("$it ")
    }
    return res.toString()
}

