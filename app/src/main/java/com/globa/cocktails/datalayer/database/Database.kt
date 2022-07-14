package com.globa.cocktails.datalayer.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import com.globa.cocktails.datalayer.models.CocktailDBModel
import com.google.gson.Gson


@Database(entities = [CocktailDBModel::class], version = 3)
    @TypeConverters (Converters::class)
    abstract class CocktailDatabase : RoomDatabase() {
        abstract val cocktailDao: CocktailDao
    }

    private lateinit var instance : CocktailDatabase

    fun getDatabase(context: Context) : CocktailDatabase{
        if (!::instance.isInitialized){
            instance = Room.databaseBuilder(context.applicationContext,
                CocktailDatabase::class.java,
                "cocktails")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
        return instance
    }

    class Converters {

        @TypeConverter
        fun listToJson(value: List<String>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
    }