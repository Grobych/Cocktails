package com.globa.cocktails.datalayer.storage

import android.content.Context
import com.globa.cocktails.datalayer.models.CocktailAPIModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import javax.inject.Inject

class CocktailFileDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getCocktails(): List<CocktailAPIModel> {
        val json = readAsset(context, "recipes.json")
        val typeToken = object : TypeToken<List<CocktailAPIModel>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    private fun readAsset(context: Context, fileName: String): String =
        context
            .assets
            .open(fileName)
            .bufferedReader()
            .use(BufferedReader::readText)
}