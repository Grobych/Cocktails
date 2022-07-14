package com.globa.cocktails.datalayer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class Cocktail(
    @PrimaryKey
    val id : String,
    val drinkNumber : Int,
    val drinkName : String,
    val alcohol : Boolean,
    val drinkCategory : String,
    val imageURL : String,
    val drinkGlass : String,
    val ingredients : List<String>,
    val measures : List<String>,
    val instructions : String)