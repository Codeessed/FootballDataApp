package com.example.footballdataapp.repository.db

import com.example.footballdataapp.model.CompetitionResponse
import com.example.footballdataapp.util.Resource

interface DatabaseDataRepository {

    suspend fun getCompetition(): Resource<CompetitionResponse>
}