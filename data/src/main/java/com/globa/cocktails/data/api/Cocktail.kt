package com.globa.cocktails.data.api

import com.globa.cocktails.database.api.model.CocktailDBModel

data class Cocktail(
    val id : Int = 0,
    val drinkName : String = "",
    val alcohol : Boolean = true,
    val drinkCategory : String = "",
    val imageURL : String = "",
    val drinkGlass : String = "",
    val ingredients : List<String> = listOf(),
    val measures : List<String> = listOf(),
    val instructions : String = "")

fun Cocktail.asDBModel(): CocktailDBModel =
    CocktailDBModel(
        id = id,
        drinkName = drinkName,
        alcohol = alcohol,
        drinkCategory = drinkCategory,
        imageURL = imageURL,
        drinkGlass = drinkGlass,
        ingredients = ingredients,
        measures = measures,
        instructions = instructions
    )