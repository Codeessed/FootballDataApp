package com.example.footballdataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballdataapp.model.ChosenAreaModel
import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.model.competition.Competition
import com.example.footballdataapp.presentation.adapters.CompetitioAdapter
import com.example.gomoneyapp.R
import com.example.gomoneyapp.databinding.ActivityAreaLeagueFragmentBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AreaLeagueActivity : AppCompatActivity(), CompetitioAdapter.AreaItemClickListener {

    private lateinit var binding: ActivityAreaLeagueFragmentBinding

    private var listOfIds = arrayListOf<Int>()
    private var listOfNames = arrayListOf<String>()
    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaLeagueFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.leagueRetry.setOnClickListener {
            dataViewModel.getCompetition(listOfIds.joinToString().replace(" ", ""))
        }

        binding.competitionBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dataViewModel.allCompetition.collectLatest { competition ->
                    when(competition){
                        is DataViewModel.CompetitionEvent.AllCompetitionSuccess -> {
                            adapterSetUp(competition.competitionResponse.competitions)
                            binding.competitionEmpty.text = getString(R.string.competition_unavailable, listOfNames.joinToString())
                            binding.leagueError.isVisible = false
                            binding.leagueProgress.isVisible = false
                            binding.leagueRetry.isVisible = false
                            if (competition.competitionResponse.competitions.isEmpty()){
                                binding.competitionSuccess.isVisible = false
                                binding.competitionEmpty.isVisible = true
                            }else{
                                binding.competitionSuccess.isVisible = true
                                binding.competitionEmpty.isVisible = false
                            }
                        }
                        is DataViewModel.CompetitionEvent.Failure -> {
                            binding.leagueError.text = competition.message
                            binding.leagueError.isVisible = true
                            binding.leagueProgress.isVisible = false
                            binding.competitionSuccess.isVisible = false
                            binding.leagueRetry.isVisible = true
                            binding.competitionEmpty.isVisible = false
                        }
                        is DataViewModel.CompetitionEvent.Loading -> {
                            binding.leagueError.isVisible = false
                            binding.leagueProgress.isVisible = true
                            binding.competitionSuccess.isVisible = false
                            binding.leagueRetry.isVisible = false
                            binding.competitionEmpty.isVisible = false
                        }
                        else -> {
                            val intent = intent.getStringExtra("areas_id")
                            listOfIds.addAll(
                                fromJsonString(intent).map {
                                    it.id
                                }
                            )
                            listOfNames.addAll(
                                fromJsonString(intent).map {
                                    it.name
                                }
                            )
                            binding.competitionTitle.text = getString(R.string.competition_available, listOfNames.joinToString())
                            dataViewModel.getCompetition(listOfIds.joinToString().replace(" ", ""))
                        }

                    }
                }
            }
        }

    }
    fun fromJsonString(string: String?): ArrayList<ChosenAreaModel>{
        val result = ArrayList<ChosenAreaModel>()
        val gson = Gson()
        val type = object: TypeToken<ArrayList<ChosenAreaModel>>() {}.type
        if (string != null){
            result.addAll(gson.fromJson(string, type))
        }
        return result
    }

    fun adapterSetUp(list: List<Competition>){
        val leagueRecycler = binding.leagueRecycler
        val leagueAdapter = CompetitioAdapter(this, this)
        leagueRecycler.adapter = leagueAdapter
        leagueRecycler.layoutManager = LinearLayoutManager(this)
        leagueAdapter.differ.submitList(list)
    }


    override fun onClick(competition: Competition) {
        var gson = Gson()
        var string = gson.toJson(competition)
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

}