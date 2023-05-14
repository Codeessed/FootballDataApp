package com.example.footballdataapp.data.network

import com.example.footballdataapp.model.areas.AreasResponse
import com.example.footballdataapp.model.competition.CompetitionResponse
import com.example.footballdataapp.util.Constants.TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v4/competitions")
    suspend fun getAllCompetition(
//        @Header("X-Auth-Token")
//        token: String? = TOKEN,
        @Query("areas") areas: String
    ): Response<CompetitionResponse>

    @GET("/v4/areas/")
    suspend fun getAllAreas(
    ): Response<AreasResponse>
}