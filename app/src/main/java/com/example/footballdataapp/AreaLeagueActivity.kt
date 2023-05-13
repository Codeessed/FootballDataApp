package com.example.footballdataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gomoneyapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AreaLeagueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_league_fragment)
    }
}