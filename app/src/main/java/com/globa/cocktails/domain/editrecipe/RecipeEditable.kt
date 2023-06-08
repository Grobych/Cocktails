package com.globa.cocktails.domain.editrecipe

import com.globa.cocktails.domain.getrecipesdetails.RecipeDetails

data class RecipeEditable(
    val id: Int = 0,
    val name: String = "",
    val ingredients: List<String> = listOf(),
    val measures: List<String> = listOf(),
    val imageURL: String = "",
    val instructions: String = ""
)

fun RecipeDetails.toReceipeEditable() = RecipeEditable(
    id = id,
    name = name,
    ingredients = ingredients,
    measures = measures,
    instructions = instructions,
    imageURL = imageURL
)
