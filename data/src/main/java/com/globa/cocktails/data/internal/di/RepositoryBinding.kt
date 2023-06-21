package com.globa.cocktails.data.internal.di

import com.globa.cocktails.data.api.CocktailRepository
import com.globa.cocktails.data.api.EditLogRepository
import com.globa.cocktails.data.internal.repository.CocktailRepositoryImpl
import com.globa.cocktails.data.internal.repository.EditLogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinding {
    @Binds
    fun bindCocktailRepository(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository
    @Binds
    fun bindEditLogRepository(editLogRepositoryImpl: EditLogRepositoryImpl): EditLogRepository
}