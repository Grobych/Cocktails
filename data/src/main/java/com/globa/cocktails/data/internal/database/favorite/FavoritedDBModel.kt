package com.globa.cocktails.data.internal.database.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorited")
data class FavoritedDBModel(
    @PrimaryKey
    val name: String
)