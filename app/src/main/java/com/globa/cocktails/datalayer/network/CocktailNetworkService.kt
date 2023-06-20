package com.globa.cocktails.datalayer.network

import retrofit2.http.GET

interface CocktailNetworkService {
    @GET("/drinks")
    suspend fun getDrinks() : List<CocktailAPIModel>
}

