package com.example.footballdataapp.repository

import com.example.footballdataapp.model.Competition
import com.example.footballdataapp.util.BoundResource
import kotlinx.coroutines.flow.Flow

interface CompetitionRepository {
    fun getCompetition(): Flow<BoundResource<List<Competition>>>
}