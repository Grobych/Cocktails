package com.globa.cocktails.datalayer.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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