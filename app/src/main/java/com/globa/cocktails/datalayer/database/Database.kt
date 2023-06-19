package com.globa.cocktails.datalayer.database

import androidx.room.*
import com.globa.cocktails.datalayer.models.CocktailDBModel
import com.globa.cocktails.datalayer.models.FavoritedDBModel
import com.google.gson.Gson

@Database(
    entities = [
        CocktailDBModel::class,
        FavoritedDBModel::class],
    version = 10,
    exportSchema = false
)
    @TypeConverters (Converters::class)
    abstract class CocktailDatabase : RoomDatabase() {
        abstract val cocktailDao: CocktailDao
        abstract val favoritedDao: FavoritedDao
    }

class Converters {

        @TypeConverter
        fun listToJson(value: List<String>?): String = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
    }