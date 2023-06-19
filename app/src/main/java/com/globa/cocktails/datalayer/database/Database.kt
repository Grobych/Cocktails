package com.globa.cocktails.datalayer.database

import androidx.room.*
import com.globa.cocktails.datalayer.models.CocktailDBModel
import com.globa.cocktails.datalayer.models.EditRecipeLogDBModel
import com.globa.cocktails.datalayer.models.FavoritedDBModel
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

class Converters {

        @TypeConverter
        fun listToJson(value: List<String>?): String = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
    }