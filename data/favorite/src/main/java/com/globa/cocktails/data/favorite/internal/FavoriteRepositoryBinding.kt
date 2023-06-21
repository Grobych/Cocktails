package com.globa.cocktails.data.favorite.internal

import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface FavoriteRepositoryBinding {
    @Binds
    fun bindFavoriteRepository(binding: FavoritedCocktailRepositoryImpl): FavoritedCocktailRepository
}