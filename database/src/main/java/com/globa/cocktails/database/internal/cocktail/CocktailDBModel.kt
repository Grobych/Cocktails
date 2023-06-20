package com.globa.cocktails.database.internal.cocktail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails", indices = [Index(value = ["drinkName"], unique = true)])
data class CocktailDBModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "drinkName")
    val drinkName : String,
    val alcohol : Boolean,
    val drinkCategory : String,
    val imageURL : String,
    val drinkGlass : String,
    val ingredients : List<String>,
    val measures : List<String>,
    val instructions : String)