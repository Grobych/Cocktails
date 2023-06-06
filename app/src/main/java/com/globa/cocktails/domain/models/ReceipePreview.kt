package com.globa.cocktails.domain.models

import com.globa.cocktails.datalayer.models.Cocktail

data class ReceipePreview(
    val id: Int,
    val name: String,
    val imageURL: String,
    val isFavorite: Boolean,
    val tags: List<String>
)

fun Cocktail.toReceipePreview() = ReceipePreview(
    id = this.id,
    name = this.drinkName,
    imageURL = this.imageURL,
    isFavorite = this.isFavorite,
    tags = this.ingredients
)
