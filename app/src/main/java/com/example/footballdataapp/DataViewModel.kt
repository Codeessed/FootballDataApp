package com.example.footballdataapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.model.competition.Competition
import com.example.footballdataapp.model.competition.CompetitionResponse
import com.example.footballdataapp.repository.CompetitionRepositoryImpl
import com.example.footballdataapp.util.BoundResource
import com.example.footballdataapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: CompetitionRepositoryImpl
): ViewModel() {
//    val competition = viewModelScope.launch {
//        _allCompetition.value = repository.getCompetition().collect{
//            CompetitionEvent.AllCompetitionSuccess(it)
//        }
//val competition = repository.getCompetition().as
//    }

//    val competition = repository.getCompetition().asLiveData()


    fun getCompetition(areas: String) {
        viewModelScope.launch {
            _allCompetition.value = CompetitionEvent.Loading
            try {
                when(val response = repository.getCompetition(areas)){
                    is Resource.Success ->{
                        _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(response.data!!)
                    }
                    is Resource.Failure ->{
                        Log.d("competition", "failure ${ response.message!! }")
                        _allCompetition.value = CompetitionEvent.Failure(response.message!!)
                    }
                    is Resource.Error ->{
                        Log.d("competition", "error ${ response.error!!.message }")
                        _allCompetition.value = CompetitionEvent.Failure(response.error!!.message)
                    }
                }
            }catch (e: java.lang.Exception){
                when(e){
                    is IOException ->{
                        _allCompetition.value = CompetitionEvent.Failure("Weak network")
                    }
                    else -> _allCompetition.value = CompetitionEvent.Failure(e.message!!)
                }
            }





//                .collect { boundResource ->
//                when(boundResource){
//                    is BoundResource.Success -> {
//                        _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(boundResource.data!!, null)
//                    }
//                    is BoundResource.Error -> {
//                        if (boundResource.data.isNullOrEmpty()){
//                            _allCompetition.value = CompetitionEvent.Failure(boundResource.error?.localizedMessage.toString())
//                        }else{
//                            _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(boundResource.data, boundResource.error?.localizedMessage.toString())
//                        }
//                    }
//                    is BoundResource.Loading -> {
//                        if (boundResource.data.isNullOrEmpty()){
//                            _allCompetition.value = CompetitionEvent.Loading
//                        }
//                    }
//                    else -> {
//                        _allCompetition.value = CompetitionEvent.Empty
//                    }
//                }
//            }
        }
    }
    fun getAreas() {
        viewModelScope.launch {
            repository.getAllArea().collect { boundResource ->
                when(boundResource){
                    is BoundResource.Success -> {
                        _allAreas.value = CompetitionEvent.AllAreasSuccess(boundResource.data!!, null)
                    }
                    is BoundResource.Error -> {
                        if (boundResource.data.isNullOrEmpty()){
                            _allAreas.value = CompetitionEvent.Failure(boundResource.error?.localizedMessage.toString())
                        }else{
                            _allAreas.value = CompetitionEvent.AllAreasSuccess(boundResource.data, boundResource.error?.localizedMessage.toString())
                        }
                    }
                    is BoundResource.Loading -> {
                        if (boundResource.data.isNullOrEmpty()){
                            _allAreas.value = CompetitionEvent.Loading
                        }
                    }
                    else -> {
                        _allAreas.value = CompetitionEvent.Empty
                    }
                }
            }
        }
    }

    private  val _allAreas = MutableStateFlow<CompetitionEvent>(CompetitionEvent.Empty)
    val allAreas = _allAreas.asStateFlow()

    private  val _allCompetition = MutableStateFlow<CompetitionEvent>(CompetitionEvent.Empty)
    val allCompetition = _allCompetition.asStateFlow()

    sealed class CompetitionEvent {
        class AllAreasSuccess(val areasResult: List<AreaData>, val error: String?) : CompetitionEvent()
        class AllCompetitionSuccess(val competitionResponse: CompetitionResponse) : CompetitionEvent()
        object Empty: CompetitionEvent()
        class Failure(val message: String) : CompetitionEvent()
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

//    fun saveCompetition(competition: Competition){
//        viewModelScope.launch {
//            dataBaseRepo.insertCompetition(competition)
//        }
//    }

//    fun getCompetition(){
//        viewModelScope.launch {
//            dataBaseRepo.getCompetition().collectLatest {
//                _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(it)
//            }
//
////            /dataBaseRepo.getCompetition().flowOn(Dispatchers.IO).stateIn(viewModelScope)
//
//        }
//    }


}