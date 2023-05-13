package com.example.footballdataapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballdataapp.model.Area
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(area: Area)

    @Query("SELECT * FROM area_table")
    suspend fun getAllArea(): Flow<List<Area>>
}