package com.example.footballdataapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballdataapp.db.dao.CompetitionDao
import com.example.footballdataapp.model.Competition
import com.example.footballdataapp.model.CompetitionResponse
import com.example.footballdataapp.repository.db.DatabaseDataImpl
import com.example.footballdataapp.repository.db.DatabaseDataRepository
import com.example.footballdataapp.repository.network.NetworkDataRepository
import com.example.footballdataapp.util.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataBaseRepo: DatabaseDataRepository,
    private val repository: NetworkDataRepository
): ViewModel() {

    private  val _allCompetition = MutableStateFlow<CompetitionEvent>(CompetitionEvent.Empty)
    val allCompetition = _allCompetition.asStateFlow()

    sealed class CompetitionEvent {
        class AllCompetitionSuccess(val competitionResponse: List<Competition>) : CompetitionEvent()
        object Empty: CompetitionEvent()
        class Failure(val message: String?) : CompetitionEvent()
        object Loading : CompetitionEvent()
    }

//    fun allCompetition(){
//        viewModelScope.launch {
//            _allCompetition.value = CompetitionEvent.Loading
//            try {
//                when(val result = repository.getAllCompetition()){
//                    is Resource.Success ->{
//                        _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(result.data!!)
//                    }
//                    is Resource.Error -> _allCompetition.value = CompetitionEvent.Failure("An error occurred, we are on it!")
//                }
//            }catch (e: Exception){
//                when(e){
//                    is IOException -> _allCompetition.value = CompetitionEvent.Failure("Weak network")
//                    else -> _allCompetition.value = CompetitionEvent.Failure(e.message)
//                }
//            }
//        }
//    }

    fun saveCompetition(competition: Competition){
        viewModelScope.launch {
            dataBaseRepo.insertCompetition(competition)
        }
    }

    fun getCompetition(){
        viewModelScope.launch {
            dataBaseRepo.getCompetition().collectLatest {
                _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(it)
            }

//            /dataBaseRepo.getCompetition().flowOn(Dispatchers.IO).stateIn(viewModelScope)

        }
    }
}