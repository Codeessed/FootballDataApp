package com.example.footballdataapp.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.footballdataapp.data.network.ApiInterface
import com.example.footballdataapp.db.database.DataDatabase
import com.example.footballdataapp.model.ErrorMessage
import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.model.competition.Competition
import com.example.footballdataapp.model.competition.CompetitionResponse
import com.example.footballdataapp.model.competition.CurrentSeason
import com.example.footballdataapp.util.BoundResource
import com.example.footballdataapp.util.Resource
import com.example.footballdataapp.util.networkBoundResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class CompetitionRepositoryImpl @Inject constructor(
    private val api: ApiInterface,
    private val db: DataDatabase
): CompetitionRepository {
    private val areaDao = db.getAreaDao()
    override suspend fun getCompetition(areas: String): Resource<CompetitionResponse>{

        var response = api.getAllCompetition(
            areas
        )

        var result = response.body()
        return try {
            when(response.code()){
                in 200..299 -> Resource.Success(result)
                in 400..500 ->{
                    val gson = Gson()
                    val type = object: TypeToken<ErrorMessage>() {}.type
                    val errorResponse: ErrorMessage = gson.fromJson(response.errorBody()?.charStream(), type)
                    Resource.Error(errorResponse)
                }
                else -> {
                    Resource.Failure(response.message())
                }
            }
        }catch (e: Exception){
            Resource.Failure("An error occurred")
        }




//        return networkBoundResource(
//            query = {
//                competitionDao.getAllCompetition()
//            },
//            fetch = {
//                delay(2000)
//                api.getAllCompetition(areas).body()!!.competitions
//            },
//            saveFetchResult = { competitionResponse ->
//                db.withTransaction {
//                    competitionDao.deleteCompetition()
//                    competitionResponse.map {competitionData ->
//                        Competition(
//                            area = competitionData.area,
//                            code = competitionData.code?:"",
////                            currentSeason = competitionData.currentSeason?: CurrentSeason(1, "", 1, "",null),
//                            emblem = competitionData.emblem?:"",
//                            id = competitionData.id,
//                            lastUpdated = competitionData.lastUpdated,
//                            name = competitionData.name,
//                            numberOfAvailableSeasons = competitionData.numberOfAvailableSeasons,
//                            plan = competitionData.plan,
//                            type = competitionData.type
//                        )
//                    }
//                    competitionDao.insertCompetition(competitionResponse)
//                }
//
//            }
//        )
    }

    override fun getAllArea(): Flow<BoundResource<List<AreaData>>> {
        return networkBoundResource(
            query = {
                areaDao.getAllArea()
            },
            fetch = {
                delay(2000)
                api.getAllAreas().body()!!.areas
            },
            saveFetchResult = { areaResponse ->
                db.withTransaction {
                    areaDao.deleteArea()
                    areaDao.insertArea(
                        areaResponse.map {areaData ->
                            AreaData(
                                countryCode = areaData.countryCode,
                                flag = areaData.flag?:"",
                                id = areaData.id,
                                name = areaData.name,
                                parentArea = areaData.parentArea?:"",
                                parentAreaId = areaData.parentAreaId?:0
                            )
                        }
                    )


                }

            }
        )
    }


}