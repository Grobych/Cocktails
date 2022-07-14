package com.globa.cocktails.datalayer.network

import com.globa.cocktails.datalayer.models.Cocktail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CocktailAPI {
    @GET("/drinks")
    suspend fun getDrinks() : List<Cocktail>
}

object CocktailNetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cocktail-recipes-tully4school.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance: CocktailAPI = retrofit.create(CocktailAPI::class.java)
}