package com.example.footballdataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballdataapp.db.dao.CompetitionDao
import com.example.footballdataapp.model.Area
import com.example.footballdataapp.model.Competition
import com.example.footballdataapp.presentation.adapters.AreaAdapter
import com.example.footballdataapp.util.BoundResource
import com.example.gomoneyapp.databinding.ActivityAreaBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AreaActivity : AppCompatActivity(), AreaAdapter.AreaItemClickListener {
    private lateinit var binding: ActivityAreaBinding

    @Inject lateinit var competitionDao: CompetitionDao

    private val dataViewModel: DataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dataViewModel.allCompetition.collectLatest { competition ->
                    when(competition){
//                        is BoundResource.Success -> {
//                            Log.d("competition", "success ${competition.error}, ${competition.data}\"")
//                            adapterSetUp(competition.data!!)
//                        }
//                        is BoundResource.Loading<*> -> {
//                            Log.d("competition", "loading ${competition.error}, ${competition.data}\"")
//                        }
//                        is BoundResource.Error<*> -> {
//                            adapterSetUp(competition.data!!)
//                            Log.d("competition", "error ${competition.error}, ${competition.data}\"")
//                        }
//                        else -> {
//                            Log.d("competition", "empty")
//                            dataViewModel.getCompetition()
//                        }
                        is DataViewModel.CompetitionEvent.AllCompetitionSuccess -> {
                            adapterSetUp(competition.competitionResponse)
                            binding.areaError.isVisible = false
                            binding.areaProgress.isVisible = false
                            binding.areaRecycler.isVisible = true
                            if (!competition.error.isNullOrEmpty()){
                                Toast.makeText(this@AreaActivity, competition.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is DataViewModel.CompetitionEvent.Failure -> {
                            binding.areaError.text = competition.message
                            binding.areaError.isVisible = true
                            binding.areaProgress.isVisible = false
                            binding.areaRecycler.isVisible = false
                        }
                        is DataViewModel.CompetitionEvent.Loading -> {
                            binding.areaError.isVisible = false
                            binding.areaProgress.isVisible = true
                            binding.areaRecycler.isVisible = false
                        }
                        else -> {
                            dataViewModel.getCompetition()
                        }

                    }
                }
            }
        }
//        dataViewModel.getCompetition()
//
//        Log.d("competition", "${ competitionDao.hashCode() }")
//        lifecycleScope.launch{
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                dataViewModel.allCompetition.collectLatest { competition ->
//                    when(competition){
//                        is DataViewModel.CompetitionEvent.AllCompetitionSuccess -> {
//                            adapterSetUp(competition.competitionResponse.competitions)
//                            Log.d("competition", competition.competitionResponse.toString())
//                        }
//                        is DataViewModel.CompetitionEvent.Failure -> {
//                            Log.d("competition", competition.message.toString())
//                        }
//                        is DataViewModel.CompetitionEvent.Loading -> {
//                            Log.d("competition", "loading")
//                        }
//                        is DataViewModel.CompetitionEvent.Empty -> {
//                            dataViewModel.allCompetition()
//                        }
//
//                    }
//                }
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun adapterSetUp(list: List<Competition>){
        val discRecycler = binding.areaRecycler
        val discountAdapter = AreaAdapter(this, this)
        discRecycler.adapter = discountAdapter
        discRecycler.layoutManager = LinearLayoutManager(this)
        discountAdapter.differ.submitList(list)
    }


    override fun onClick(competition: Competition) {
        var gson = Gson()
        var string = gson.toJson(competition.area)
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()

    }

}