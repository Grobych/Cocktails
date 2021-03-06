package com.globa.cocktails.datalayer.database

import androidx.room.*
import androidx.room.Database
import com.globa.cocktails.datalayer.models.CocktailDBModel
import com.google.gson.Gson
import dagger.Module

@Module
@Database(entities = [CocktailDBModel::class], version = 3, exportSchema = false)
    @TypeConverters (Converters::class)
    abstract class CocktailDatabase : RoomDatabase() {
        abstract val cocktailDao: CocktailDao
    }

class Converters {

        @TypeConverter
        fun listToJson(value: List<String>?): String = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
    }