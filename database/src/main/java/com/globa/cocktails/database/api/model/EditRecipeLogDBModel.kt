package com.globa.cocktails.database.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edit_logs")
data class EditRecipeLogDBModel(
    @PrimaryKey
    val name: String,
    val dateTime: Long
)
