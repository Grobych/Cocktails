package com.globa.cocktails.data.internal.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindCocktailRepository(cocktailRepositoryImpl: com.globa.cocktails.data.internal.repository.CocktailRepositoryImpl): com.globa.cocktails.data.api.CocktailRepository

    @Binds
    fun bindFavoritesRepository(favoritedCocktailRepositoryImpl: com.globa.cocktails.data.internal.repository.FavoritedCocktailRepositoryImpl): com.globa.cocktails.data.api.FavoritedCocktailRepository

    @Binds
    fun bindEditLogRepository(editLogRepositoryImpl: com.globa.cocktails.data.internal.repository.EditLogRepositoryImpl): com.globa.cocktails.data.api.EditLogRepository
}