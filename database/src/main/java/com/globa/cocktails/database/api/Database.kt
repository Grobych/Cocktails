package com.globa.cocktails.database.api

import androidx.room.*
import com.globa.cocktails.database.api.model.CocktailDBModel
import com.globa.cocktails.database.api.model.EditRecipeLogDBModel
import com.globa.cocktails.database.api.model.FavoritedDBModel
import com.globa.cocktails.database.internal.cocktail.CocktailDao
import com.globa.cocktails.database.internal.editlog.EditLogDao
import com.globa.cocktails.database.internal.favorite.FavoritedDao
import com.google.gson.Gson

@Database(
    entities = [
        CocktailDBModel::class,
        FavoritedDBModel::class,
        EditRecipeLogDBModel::class],
    version = 11,
    exportSchema = false
)
    @TypeConverters (Converters::class)
    abstract class CocktailDatabase : RoomDatabase() {
        abstract val cocktailDao: CocktailDao
        abstract val favoritedDao: FavoritedDao
        abstract val editLogDao: EditLogDao
    }

internal class Converters {

        @TypeConverter
        fun listToJson(value: List<String>?): String = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
    }