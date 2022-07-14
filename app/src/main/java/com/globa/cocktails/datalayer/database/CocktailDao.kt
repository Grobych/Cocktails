package com.globa.cocktails.datalayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globa.cocktails.datalayer.models.CocktailDBModel

@Dao
interface CocktailDao{
    @Query("select * from cocktails")
    fun getCocktails() : List<CocktailDBModel>
    @Query("select * from cocktails where drinkName like :name")
    fun getCocktailByName(name : String) : List<CocktailDBModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(cocktails : List<CocktailDBModel>)
}