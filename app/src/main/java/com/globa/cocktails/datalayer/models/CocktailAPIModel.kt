package com.globa.cocktails.datalayer.models

import com.google.gson.annotations.SerializedName

data class CocktailAPIModel(
    @SerializedName("_id")              val id : String,
    @SerializedName("drinkNumber")      val drinkNumber : Int,
    @SerializedName("drinkName")        val drinkName : String,
    @SerializedName("alcohol")          val alcohol : Boolean,
    @SerializedName("drinkCategory")    val drinkCategory : String,
    @SerializedName("drinkThumb")       val imageURL : String,
    @SerializedName("drinkGlass")       val drinkGlass : String,
    @SerializedName("drinkIngredients") val ingredients : List<String>,
    @SerializedName("drinkMeasures")    val measures : List<String>,
    @SerializedName("drinkInstructions")val instructions : String)

    fun List<CocktailAPIModel>.asDomainModel() = map {
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
            instructions = it.instructions,
            isFavorite = false
        )
    }

    fun List<CocktailAPIModel>.asDBModel() = map {
        CocktailDBModel(
            id = it.id,
            drinkNumber = it.drinkNumber,
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