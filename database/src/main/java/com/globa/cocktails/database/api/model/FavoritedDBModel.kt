package com.globa.cocktails.database.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorited")
data class FavoritedDBModel(
    @PrimaryKey
    val name: String
)