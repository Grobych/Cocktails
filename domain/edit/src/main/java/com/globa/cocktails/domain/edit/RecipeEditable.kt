package com.globa.cocktails.domain.edit

data class RecipeEditable(
    val id: Int = 0,
    val name: String = "",
    val ingredients: List<String> = listOf(),
    val measures: List<String> = listOf(),
    val imageURL: String = "",
    val instructions: String = ""
)
