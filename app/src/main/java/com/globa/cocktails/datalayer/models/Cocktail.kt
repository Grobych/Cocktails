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
    val instructions : String = "")

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