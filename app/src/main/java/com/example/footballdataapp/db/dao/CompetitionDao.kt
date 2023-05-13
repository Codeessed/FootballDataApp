package com.example.footballdataapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballdataapp.model.Area
import com.example.footballdataapp.model.Competition
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetition(competition: Competition)

    @Query("SELECT * FROM competition_table")
    suspend fun getAllArea(): Flow<List<Competition>>

}