package com.globa.cocktails.domain.recipedetails

data class RecipeDetails(
    val id: Int = 0,
    val name: String = "",
    val ingredients: List<String> = listOf(),
    val measures: List<String> = listOf(),
    val tags: Map<String, RecipeDetailsTagType>,
    val instructions: String = "",
    val imageURL: String = "",
    val isFavorite: Boolean = false
)
enum class RecipeDetailsTagType {GLASS, CATEGORY}