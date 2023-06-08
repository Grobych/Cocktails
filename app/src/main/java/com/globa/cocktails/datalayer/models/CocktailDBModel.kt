package com.globa.cocktails.datalayer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globa.cocktails.domain.Cocktail

@Entity(tableName = "cocktails")
data class CocktailDBModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val drinkName : String,
    val alcohol : Boolean,
    val drinkCategory : String,
    val imageURL : String,
    val drinkGlass : String,
    val ingredients : List<String>,
    val measures : List<String>,
    val instructions : String,
    val isFavorite : Boolean)

    fun List<CocktailDBModel>.asDomainModel() = map {
        Cocktail(
            id = it.id,
            drinkName = it.drinkName,
            alcohol = it.alcohol,
            drinkCategory = it.drinkCategory,
            imageURL = it.imageURL,
            drinkGlass = it.drinkGlass,
            ingredients = it.ingredients,
            measures = it.measures,
            instructions = it.instructions,
            isFavorite = it.isFavorite
        )
    }

    fun CocktailDBModel.asDomainModel() =
        Cocktail(
            id = id,
            drinkName = drinkName,
            alcohol = alcohol,
            drinkCategory = drinkCategory,
            imageURL = imageURL,
            drinkGlass = drinkGlass,
            ingredients = ingredients,
            measures = measures,
            instructions = instructions,
            isFavorite = isFavorite
        )

fun List<CocktailAPIModel>.asDBModel() = map {
    CocktailDBModel(
        id = 0,
        drinkName = it.drinkName,
        alcohol = it.alcohol,
        drinkCategory = it.drinkCategory,
        imageURL = it.imageURL,
        drinkGlass = it.drinkGlass,
        ingredients = it.ingredients,
        measures = it.measures,
        instructions = it.instructions,
        isFavorite = false
    )
}