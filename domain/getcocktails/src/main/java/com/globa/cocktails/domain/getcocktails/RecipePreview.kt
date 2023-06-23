package com.globa.cocktails.domain.getcocktails

import com.globa.cocktails.data.cocktail.api.Cocktail

//TODO: remove isFavorite
data class RecipePreview(
    val id: Int = 0,
    val name: String = "",
    val imageURL: String = "",
    val isFavorite: Boolean = false,
    val tags: List<String> = listOf()
)

fun Cocktail.toReceipePreview() = RecipePreview(
    id = this.id,
    name = this.drinkName,
    imageURL = this.imageURL,
    tags = this.ingredients
)
