package com.globa.cocktails.domain.favorites

import com.globa.cocktails.datalayer.database.favorite.FavoritedDBModel

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
