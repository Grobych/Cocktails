package com.globa.cocktails.ui

import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import javax.inject.Inject

class FakeCocktailRepository @Inject constructor(): CocktailRepository {
    override suspend fun getCocktails(): List<Cocktail> {
        return listOf(
            Cocktail(drinkName = "Margarita", id = "1"),
            Cocktail(drinkName = "Martini", id = "2"),
            Cocktail(drinkName = "Cuba Libre", id = "3")
        )
    }
}