package com.globa.cocktails.datalayer.network

import com.globa.cocktails.datalayer.models.CocktailAPIModel
import retrofit2.http.GET

interface CocktailNetworkService {
    @GET("/drinks")
    suspend fun getDrinks() : List<CocktailAPIModel>
}

