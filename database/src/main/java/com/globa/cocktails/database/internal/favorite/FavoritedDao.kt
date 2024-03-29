package com.globa.cocktails.database.internal.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globa.cocktails.database.api.model.FavoritedDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritedDao {

    @Query("select * from favorited")
    fun getFavorited() : Flow<List<FavoritedDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorited : FavoritedDBModel)

    @Delete
    fun delete(favorited: FavoritedDBModel)
}