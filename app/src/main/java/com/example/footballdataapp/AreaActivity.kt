package com.example.footballdataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballdataapp.model.Area
import com.example.footballdataapp.model.Competition
import com.example.footballdataapp.presentation.adapters.AreaAdapter
import com.example.gomoneyapp.databinding.ActivityAreaBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AreaActivity : AppCompatActivity(), AreaAdapter.AreaItemClickListener {
    private lateinit var binding: ActivityAreaBinding


    private val dataViewModel: DataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dataViewModel.allCompetition.collectLatest { competition ->
                    when(competition){
                        is DataViewModel.CompetitionEvent.AllCompetitionSuccess -> {
                            adapterSetUp(competition.competitionResponse.competitions)
                            Log.d("competition", competition.competitionResponse.toString())
                        }
                        is DataViewModel.CompetitionEvent.Failure -> {
                            Log.d("competition", competition.message.toString())
                        }
                        is DataViewModel.CompetitionEvent.Loading -> {
                            Log.d("competition", "loading")
                        }
                        is DataViewModel.CompetitionEvent.Empty -> {
                            dataViewModel.allCompetition()
                        }

                    }
                }
            }
        }
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