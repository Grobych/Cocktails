package com.globa.cocktails.datalayer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailDBModel(
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

    fun List<CocktailDBModel>.asDomainModel() = map {
        Cocktail(
            id = it.id,
            drinkNumber = it.drinkNumber,
            drinkName = it.drinkName,
            alcohol = it.alcohol,
            drinkCategory = it.drinkCategory,
            imageURL = it.imageURL,
            drinkGlass = it.drinkGlass,
            ingredients = it.ingredients,
            measures = it.measures,
            instructions = it.instructions
        )
    }
