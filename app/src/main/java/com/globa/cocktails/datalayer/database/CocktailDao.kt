package com.globa.cocktails.datalayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.globa.cocktails.datalayer.models.CocktailDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao{
    @Query("select * from cocktails")
    fun getCocktails() : Flow<List<CocktailDBModel>>
    @Query("select * from cocktails where id = :id")
    fun getCocktailById(id: String): Flow<CocktailDBModel>
    @Insert(onConflict = REPLACE)
    fun insert(cocktail: CocktailDBModel)
    @Insert(onConflict = REPLACE)
    fun insertAll(cocktails : List<CocktailDBModel>)
    @Update(onConflict = REPLACE)
    fun update(cocktail: CocktailDBModel)

}