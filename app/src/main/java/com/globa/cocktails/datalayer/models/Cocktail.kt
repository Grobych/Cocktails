package com.globa.cocktails.datalayer.models

data class Cocktail(
    val id : String = "",
    val drinkNumber : Int = 1,
    val drinkName : String = "",
    val alcohol : Boolean = true,
    val drinkCategory : String = "",
    val imageURL : String = "",
    val drinkGlass : String = "",
    val ingredients : List<String> = listOf(),
    val measures : List<String> = listOf(),
    val instructions : String = "",
    val isFavorite : Boolean = false)


fun Cocktail.createTagLine(): String {
    val res = StringBuilder()
        .append("$drinkName ")
        .append("$drinkCategory ")
        .append("$drinkGlass ")
    ingredients.forEach {
        res.append("$it ")
    }
    return res.toString()
}

fun Cocktail.asDBModel(): CocktailDBModel = CocktailDBModel(
    id = id,
    drinkNumber = drinkNumber,
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