package com.globa.cocktails.di.modules

import android.content.Context
import androidx.room.Room
import com.globa.cocktails.datalayer.database.CocktailDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule{
    @Provides
    @Singleton
    fun getDatabase(context: Context) : CocktailDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CocktailDatabase::class.java,
            "cocktails"
        )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}