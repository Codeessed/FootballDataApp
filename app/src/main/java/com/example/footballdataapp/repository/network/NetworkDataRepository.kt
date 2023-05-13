package com.example.footballdataapp.repository.network

import com.example.footballdataapp.model.CompetitionResponse
import com.example.footballdataapp.util.Resource

interface NetworkDataRepository {

    suspend fun getAllCompetition(): Resource<CompetitionResponse>
}