package com.globa.cocktails.data.internal.di

import com.globa.cocktails.data.internal.network.CocktailNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providesCocktailNetworkService() : CocktailNetworkService {
        return Retrofit.Builder()
            .baseUrl("https://cocktail-recipes-tully4school.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailNetworkService::class.java)
    }
}