package com.globa.cocktails.di.modules

import android.content.Context
import androidx.room.Room
import com.globa.cocktails.datalayer.database.CocktailDatabase
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule{
    private lateinit var instance : CocktailDatabase

    @Provides
    fun getDatabase(context: Context) : CocktailDatabase {
        if (!DatabaseModule::instance.isInitialized){
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