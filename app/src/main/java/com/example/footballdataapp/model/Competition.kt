package com.example.footballdataapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition_table")
data class Competition(
    val area: Area,
    val code: String,
    val currentSeason: CurrentSeason,
    val emblem: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val numberOfAvailableSeasons: Int,
    val plan: String,
    val type: String
)