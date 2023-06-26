package com.globa.cocktails.feature.edit.internal

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CocktailRedactorViewModelBinding {
    @Binds
    fun bind(cocktailRedactorViewModelImpl: CocktailRedactorViewModelImpl): CocktailRedactorViewModel
}