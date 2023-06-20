package com.globa.cocktails.data.internal.di

import android.content.Context
import androidx.room.Room
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
    fun getDatabase(@ApplicationContext applicationContext: Context) : com.globa.cocktails.data.internal.database.CocktailDatabase =
        Room.databaseBuilder(
            applicationContext,
            com.globa.cocktails.data.internal.database.CocktailDatabase::class.java,
            "cocktails"
        )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}