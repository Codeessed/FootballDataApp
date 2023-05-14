package com.example.footballdataapp.repository

import androidx.room.withTransaction
import com.example.footballdataapp.data.network.ApiInterface
import com.example.footballdataapp.db.database.DataDatabase
import com.example.footballdataapp.model.Competition
import com.example.footballdataapp.util.BoundResource
import com.example.footballdataapp.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompetitionRepositoryImpl @Inject constructor(
    private val api: ApiInterface,
    private val db: DataDatabase
): CompetitionRepository {
    private val competitionDao = db.getCompetitionDao()
    override fun getCompetition(): Flow<BoundResource<List<Competition>>>{
        return networkBoundResource(
            query = {
                competitionDao.getAllCompetition()
            },
            fetch = {
                delay(2000)
                api.getAllCompetition().body()!!.competitions
            },
            saveFetchResult = { competitionResponse ->
                db.withTransaction {
                    competitionDao.deleteCompetition()
                    competitionDao.insertCompetition(competitionResponse)
                }

            }
        )
    }



}