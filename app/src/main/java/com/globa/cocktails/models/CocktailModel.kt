package com.globa.cocktails.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "cocktails")
data class Cocktail(
    @PrimaryKey
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

