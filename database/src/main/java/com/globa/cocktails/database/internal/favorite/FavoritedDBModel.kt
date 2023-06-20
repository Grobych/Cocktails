package com.globa.cocktails.database.internal.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorited")
data class FavoritedDBModel(
    @PrimaryKey
    val name: String
)