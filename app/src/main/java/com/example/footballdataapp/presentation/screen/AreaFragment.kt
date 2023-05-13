package com.example.footballdataapp.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.footballdataapp.DataViewModel
import com.example.gomoneyapp.databinding.FragmentAreaBinding
import com.example.gomoneyapp.databinding.FragmentAreaLeagueBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AreaFragment: Fragment() {
    private var _binding: FragmentAreaBinding? = null
    private val binding get() = _binding!!

    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}