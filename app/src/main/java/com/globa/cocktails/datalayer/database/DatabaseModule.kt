package com.globa.cocktails.datalayer.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule{
    private lateinit var instance : CocktailDatabase

    @Provides
    fun getDatabase(context: Context) : CocktailDatabase {
        if (!::instance.isInitialized){
            instance = Room.databaseBuilder(
                context.applicationContext,
                CocktailDatabase::class.java,
                "cocktails"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
        return instance
    }
}