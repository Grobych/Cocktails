package com.globa.cocktails.datalayer.models

data class CocktailFilter(
    var name : String = "",
    var type : String = "All",
    var hasAlcohol : String = "All"
//    val ingredients : List<String> = listOf()
)
