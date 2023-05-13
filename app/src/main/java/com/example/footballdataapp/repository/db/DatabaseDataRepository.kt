package com.example.footballdataapp.repository.db

import com.example.footballdataapp.model.Competition
import kotlinx.coroutines.flow.Flow

interface DatabaseDataRepository {

    suspend fun getCompetition(): Flow<List<Competition>>
    suspend fun insertCompetition(competition: Competition)
}