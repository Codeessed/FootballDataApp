package com.example.footballdataapp.repository

import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.model.competition.Competition
import com.example.footballdataapp.model.competition.CompetitionResponse
import com.example.footballdataapp.util.BoundResource
import com.example.footballdataapp.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CompetitionRepository {
    suspend fun getCompetition(areas: String): Resource<CompetitionResponse>
    fun getAllArea(): Flow<BoundResource<List<AreaData>>>
}