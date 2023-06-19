package com.globa.cocktails.di.modules

import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.datalayer.repository.CocktailRepositoryImpl
import com.globa.cocktails.datalayer.repository.FavoritedCocktailRepository
import com.globa.cocktails.datalayer.repository.FavoritedCocktailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindCocktailRepository(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository

    @Binds
    fun bindFavoritesRepository(favoritedCocktailRepositoryImpl: FavoritedCocktailRepositoryImpl): FavoritedCocktailRepository
}