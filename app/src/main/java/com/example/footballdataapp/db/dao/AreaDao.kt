package com.example.footballdataapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballdataapp.model.areas.AreaData
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(areaData: List<AreaData>)

    @Query("SELECT * FROM area_table")
    fun getAllArea(): Flow<List<AreaData>>

    @Query("DELETE FROM area_table")
    fun deleteArea()

}