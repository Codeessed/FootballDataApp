package com.example.footballdataapp.presentation.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.footballdataapp.DataViewModel
import com.example.footballdataapp.model.ChosenAreaModel
import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.util.SharedPreference
import com.example.gomoneyapp.databinding.ActivityAreaBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AreaActivity : AppCompatActivity(){
    private lateinit var binding: ActivityAreaBinding


    private lateinit var chipGroup: ChipGroup

    private var chosenAreaIds = ArrayList<ChosenAreaModel>()
    private var listOfAreas = ArrayList<AreaData>()
    private val dataViewModel: DataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPreference.initSharedPreference(this)
        chipGroup = binding.areaChipGroup
        binding.areasRetry.setOnClickListener {
            dataViewModel.getAreas()
        }
        binding.refreshLayout.setOnRefreshListener {
            dataViewModel.getAreas()
        }
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dataViewModel.allAreas.collectLatest { areas ->
                    when(areas){
                        is DataViewModel.CompetitionEvent.AllAreasSuccess -> {
                            binding.refreshLayout.isRefreshing = false
                            listOfAreas.clear()
                            listOfAreas.addAll(areas.areasResult)
                            if (chipGroup.size != listOfAreas.size){
                                setupChipGroupDynamically(listOfAreas)
                            }
                            binding.areaErrorBox.isVisible = false
                            binding.areaProgress.isVisible = false
                            binding.successBox.isVisible = true
                        }
                        is DataViewModel.CompetitionEvent.Failure -> {
                            binding.refreshLayout.isRefreshing = false
                            binding.areaError.text = areas.message
                            binding.areaErrorBox.isVisible = true
                            binding.areaProgress.isVisible = false
                            binding.successBox.isVisible = false
                        }
                        is DataViewModel.CompetitionEvent.Loading -> {
                            binding.areaErrorBox.isVisible = false
                            binding.areaProgress.isVisible = true
                            binding.successBox.isVisible = false
                        }
                        else -> {
                            dataViewModel.getAreas()
                        }

                    }
                }
            }
        }
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                dataViewModel.areaError.collectLatest { error ->
                    Toast.makeText(this@AreaActivity, error, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun setupChipGroupDynamically(list: ArrayList<AreaData>) {
        list.forEach {areaData ->
            chipGroup.addView(createChip(areaData.name))
        }
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            binding.proceedBtn.isEnabled = checkedIds.isNotEmpty()
            SharedPreference.saveAreaIds(
                toJsonString(
                    checkedIds.map {checkedId ->
                        ChosenAreaModel(checkedId, list[checkedId-1].id, list[checkedId-1].name)
                    }
                )
            )
        }
        fromJsonString(SharedPreference.getAreaIds()).forEach {
            chipGroup.check(it.position)
        }
        binding.proceedBtn.setOnClickListener {
            startActivity(
                Intent(this, AreaLeagueActivity::class.java)
                    .putExtra("areas_id", SharedPreference.getAreaIds())
            )
        }
    }


    private fun createChip(label: String): Chip {
        val chip = Chip(this, null, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Filter)
        chip.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        chip.text = label
        chip.isChipIconVisible = true
        chip.isCheckable = true
        chip.isClickable = true
        return chip
    }

    fun toJsonString(list: List<ChosenAreaModel>): String{
        val gson = Gson()
        return gson.toJson(list)
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

    override fun onResume() {
        super.onResume()
    }

}