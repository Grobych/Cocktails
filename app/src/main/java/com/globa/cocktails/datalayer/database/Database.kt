package com.globa.cocktails.datalayer.database

import androidx.room.*
import com.globa.cocktails.datalayer.database.cocktail.CocktailDBModel
import com.globa.cocktails.datalayer.database.cocktail.CocktailDao
import com.globa.cocktails.datalayer.database.editlog.EditLogDao
import com.globa.cocktails.datalayer.database.editlog.EditRecipeLogDBModel
import com.globa.cocktails.datalayer.database.favorite.FavoritedDBModel
import com.globa.cocktails.datalayer.database.favorite.FavoritedDao
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