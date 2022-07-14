package com.globa.cocktails.utils

import android.content.Context
import com.globa.cocktails.datalayer.models.Cocktail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class JSONReader(private val context: Context){
    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun readCocktailFromJSON(): List<Cocktail> {
        val jsonFileString = getJsonDataFromAsset( "recipes.json")
        val gson = Gson()
        val listCocktailType = object : TypeToken<List<Cocktail>>() {}.type
        return gson.fromJson(jsonFileString, listCocktailType)
    }
}


