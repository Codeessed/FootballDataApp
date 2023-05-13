package com.example.footballdataapp.repository.db

import com.example.footballdataapp.data.network.ApiInterface
import com.example.footballdataapp.db.dao.CompetitionDao
import com.example.footballdataapp.model.Competition
import com.example.footballdataapp.model.CompetitionResponse
import com.example.footballdataapp.util.Resource
import javax.inject.Inject

class DatabaseDataImpl @Inject constructor(
    private val competitionDao: CompetitionDao
): DatabaseDataRepository {
    override suspend fun getCompetition()  =
        competitionDao.getAllArea()

    override suspend fun insertCompetition(competition: Competition) {
        competitionDao.insertCompetition(competition)
    }


}