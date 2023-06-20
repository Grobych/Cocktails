package com.globa.cocktails.filestorage.api

import com.google.gson.annotations.SerializedName

data class CocktailAPIModel(
    @SerializedName("drinkName")        val drinkName : String,
    @SerializedName("alcohol")          val alcohol : Boolean,
    @SerializedName("drinkCategory")    val drinkCategory : String,
    @SerializedName("drinkThumb")       val imageURL : String,
    @SerializedName("drinkGlass")       val drinkGlass : String,
    @SerializedName("drinkIngredients") val ingredients : List<String>,
    @SerializedName("drinkMeasures")    val measures : List<String>,
    @SerializedName("drinkInstructions")val instructions : String)