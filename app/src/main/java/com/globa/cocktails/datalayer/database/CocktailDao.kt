package com.globa.cocktails.datalayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.globa.cocktails.datalayer.models.CocktailDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao{
    @Query("select * from cocktails")
    fun getCocktails() : Flow<List<CocktailDBModel>>
    @Query("select * from cocktails where drinkName like :name")
    fun getCocktailByName(name : String) : List<CocktailDBModel>
    @Query("select * from cocktails where isFavorite = true")
    fun getFavoriteCocktails(): List<CocktailDBModel>
    @Query("select * from cocktails where id = :id")
    fun getCocktailById(id: String): Flow<CocktailDBModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(cocktails : List<CocktailDBModel>)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cocktail: CocktailDBModel)
}