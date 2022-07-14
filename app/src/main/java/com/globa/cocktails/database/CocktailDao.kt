package com.globa.cocktails.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globa.cocktails.models.Cocktail

@Dao
interface CocktailDao{
    @Query("select * from cocktails")
    fun getCocktails() : List<Cocktail>
    @Query("select * from cocktails where drinkName like :name")
    fun getCocktailByName(name : String) : List<Cocktail>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(cocktails : List<Cocktail>)
}