package com.globa.cocktails.datalayer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edit_logs")
data class EditRecipeLogDBModel(
    @PrimaryKey
    val name: String,
    val dateTime: Long
)
