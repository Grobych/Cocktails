package com.globa.cocktails.datalayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globa.cocktails.datalayer.models.EditRecipeLogDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EditLogDao {
    @Query("select * from edit_logs")
    fun getEditLogs(): Flow<List<EditRecipeLogDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEditRecipeLog(log: EditRecipeLogDBModel)
}