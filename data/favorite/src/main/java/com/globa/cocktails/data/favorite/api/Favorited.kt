package com.globa.cocktails.data.favorite.api

import com.globa.cocktails.database.api.model.FavoritedDBModel

data class Favorited(
    val name: String
)

fun FavoritedDBModel.asDomainModel(): Favorited =
    Favorited(
        name = this.name
    )

fun Favorited.asDBModel() = FavoritedDBModel(
    name = name
)
