package com.globa.cocktails.network

import com.globa.cocktails.models.Cocktail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkService {
    @GET("drinks")
    suspend fun getDrinks() : List<Cocktail>
}

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("cocktail-recipes-tully4school.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retrofit.create(NetworkService::class.java)
}