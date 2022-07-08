package com.globa.cocktails.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database
import com.globa.cocktails.models.Cocktail
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken




class Database {

    private lateinit var instance : CocktailDatabase

    @Dao
    interface CocktailDao{
        @Query("select * from cocktails")
        fun getCocktails() : LiveData<List<Cocktail>>
    }

    @Database(entities = [Cocktail::class], version = 1)
    @TypeConverters (Converters::class)
    abstract class CocktailDatabase : RoomDatabase() {
        abstract val cocktailDao: CocktailDao
    }

    fun getDatabase(context: Context) : CocktailDatabase{
        if (!::instance.isInitialized){
            instance = Room.databaseBuilder(context.applicationContext,
            CocktailDatabase::class.java,
            "cocktails").build()
        }
        return instance
    }


    class Converters {

        @TypeConverter
        fun listToJson(value: List<String>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
    }
}