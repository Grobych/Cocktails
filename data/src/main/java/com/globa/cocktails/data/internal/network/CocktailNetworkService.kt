package com.globa.cocktails.data.internal.network

import retrofit2.http.GET

interface CocktailNetworkService {
    @GET("/drinks")
    suspend fun getDrinks() : List<CocktailAPIModel>
}

