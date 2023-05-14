package com.example.footballdataapp.data.network

import com.example.footballdataapp.model.CompetitionResponse
import com.example.footballdataapp.util.Constants.TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {

    @GET("/v4/competitions")
    suspend fun getAllCompetition(
        @Header("X-Auth-Token")
        token: String? = TOKEN
    ): Response<CompetitionResponse>
}