package com.example.footballdataapp.data.network

import com.example.footballdataapp.model.CompetitionResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/v4/competitions")
    suspend fun getAllCompetition(): Response<CompetitionResponse>
}