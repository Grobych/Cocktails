package com.globa.cocktails.data.api

import com.globa.cocktails.data.internal.database.favorite.FavoritedDBModel

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
