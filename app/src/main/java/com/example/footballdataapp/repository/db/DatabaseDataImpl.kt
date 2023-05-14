package com.example.footballdataapp.repository.db
//
//import com.example.footballdataapp.data.network.ApiInterface
//import com.example.footballdataapp.db.dao.CompetitionDao
//import com.example.footballdataapp.model.Competition
//import com.example.footballdataapp.model.CompetitionResponse
//import com.example.footballdataapp.util.Resource
//import com.example.footballdataapp.util.networkBoundResource
//import kotlinx.coroutines.delay
//import javax.inject.Inject
//
//class DatabaseDataImpl @Inject constructor(
//    private val competitionDao: CompetitionDao
//): DatabaseDataRepository {
//    override suspend fun getCompetition() {
//        networkBoundResource(
//            query = {
//                dataBaseRepo.getCompetition()
//            },
//            fetch = {
//                delay(2000)
//                repository.getAllCompetition()
//            },
//            saveFetchResult = {result ->
//                dataBaseRepo.wi
//
//            }
//        )
//    }
////    fun getCompetition() =
//
//
//    override suspend fun insertCompetition(competition: Competition) {
//        competitionDao.insertCompetition(competition)
//    }
//
//
//}