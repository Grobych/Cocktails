package com.globa.cocktails.datalayer.database.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorited")
data class FavoritedDBModel(
    @PrimaryKey
    val name: String
)