package com.globa.cocktails.feature.details.internal

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CocktailViewModelBinding {
    @Binds
    fun bind(cocktailViewModelImpl: CocktailViewModelImpl): CocktailViewModel
}