package com.globa.cocktails.di.modules

import android.content.Context
import androidx.room.Room
import com.globa.cocktails.datalayer.database.CocktailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule{
    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext applicationContext: Context) : CocktailDatabase =
        Room.databaseBuilder(
            applicationContext,
            CocktailDatabase::class.java,
            "cocktails"
        )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}