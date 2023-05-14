package com.example.footballdataapp.repository.network

import com.example.footballdataapp.data.network.ApiInterface
import com.example.footballdataapp.model.CompetitionResponse
import com.example.footballdataapp.util.Resource
import javax.inject.Inject

//class NetworkDataImpl @Inject constructor(
//    private val apiInterface: ApiInterface
//): NetworkDataRepository {
//    override suspend fun getAllCompetition(): Resource<CompetitionResponse> {
//        var result = apiInterface.getAllCompetition().body()
//        return Resource.Success(result)
//    }
//}