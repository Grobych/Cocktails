package com.globa.cocktails.datalayer.storage

import android.content.Context
import com.globa.cocktails.datalayer.models.CocktailAPIModel
import com.globa.cocktails.di.modules.IoDispatcher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import javax.inject.Inject

class CocktailFileDataSource @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val context: Context
) {
    suspend fun getCocktails(): List<CocktailAPIModel> = withContext(coroutineDispatcher) {
        val json = readAsset(context, "recipes.json")
        val typeToken = object : TypeToken<List<CocktailAPIModel>>() {}.type
        return@withContext Gson().fromJson(json, typeToken)
    }

    private fun readAsset(context: Context, fileName: String): String =
        context
            .assets
            .open(fileName)
            .bufferedReader()
            .use(BufferedReader::readText)
}