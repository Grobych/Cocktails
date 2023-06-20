package com.globa.cocktails.filestorage.internal

import com.globa.cocktails.filestorage.api.CocktailAPIModel
import com.globa.cocktails.filestorage.api.FileStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class FileStorageImpl @Inject constructor(
    private val fileReader: FileReader
): FileStorage {
    private val json = "recipes.json"

    override fun getCocktails(): Flow<List<CocktailAPIModel>> = fileReader.getCocktails(json)
}