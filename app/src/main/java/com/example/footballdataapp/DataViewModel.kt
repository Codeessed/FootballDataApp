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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: CompetitionRepositoryImpl
): ViewModel() {


    fun getCompetition(areas: String) {
        viewModelScope.launch {
            _allCompetition.value = CompetitionEvent.Loading
            try {
                when(val response = repository.getCompetition(areas)){
                    is Resource.Success ->{
                        _allCompetition.value = CompetitionEvent.AllCompetitionSuccess(response.data!!)
                    }
                    is Resource.Failure ->{
                        _allCompetition.value = CompetitionEvent.Failure(response.message!!)
                    }
                    is Resource.Error ->{
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

        }
    }
    fun getAreas() {
        viewModelScope.launch {
            repository.getAllArea().collect { boundResource ->
                when(boundResource){
                    is BoundResource.Success -> {
                        _allAreas.value = CompetitionEvent.AllAreasSuccess(boundResource.data!!)
                    }
                    is BoundResource.Error -> {
                        if (boundResource.data.isNullOrEmpty()){
                            _allAreas.value = CompetitionEvent.Failure("Oops, check internet connection and \n click retry to try  again")
                        }else{
                            _areaError.send("Oops, check internet connection and \n try pulling to refresh")
                            _allAreas.value = CompetitionEvent.AllAreasSuccess(boundResource.data)
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

    private  val _areaError = Channel<String>()
    val areaError = _areaError.receiveAsFlow()

    private  val _allAreas = MutableStateFlow<CompetitionEvent>(CompetitionEvent.Empty)
    val allAreas = _allAreas.asStateFlow()

    private  val _allCompetition = MutableStateFlow<CompetitionEvent>(CompetitionEvent.Empty)
    val allCompetition = _allCompetition.asStateFlow()

    sealed class CompetitionEvent {
        class AllAreasSuccess(val areasResult: List<AreaData>) : CompetitionEvent()
        class AllCompetitionSuccess(val competitionResponse: CompetitionResponse) : CompetitionEvent()
        object Empty: CompetitionEvent()
        class Failure(val message: String) : CompetitionEvent()
        object Loading : CompetitionEvent()
    }


}