package com.globa.cocktails.data.cocktail.internal

import com.globa.cocktails.data.cocktail.api.CocktailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CocktailRepositoryBinding {
    @Binds
    fun bindCocktailRepository(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository
}