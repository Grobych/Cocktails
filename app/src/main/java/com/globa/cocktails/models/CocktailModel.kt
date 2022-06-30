package com.globa.cocktails.models

data class Cocktail(val id : Int,
                    val drinkNumber : Int,
                    val drinkName : String,
                    val alcohol : Boolean,
                    val drinkCategory : String,
                    val imageURL : String,
                    val drinkGlass : String,
                    val ingredients : List<String>,
                    val measures : List<String>,
                    val instructions : String)

