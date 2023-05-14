package com.example.footballdataapp.model.competition

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Competition(
    val area: Area,
    val code: String?,
    val currentSeason: CurrentSeason?,
    val emblem: String?,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val numberOfAvailableSeasons: Int,
    val plan: String,
    val type: String
)