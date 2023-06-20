package com.globa.cocktails.filestorage.api

import kotlinx.coroutines.flow.Flow


interface FileStorage {
    fun getCocktails(): Flow<List<CocktailAPIModel>>
}