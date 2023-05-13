package com.example.footballdataapp.model

import androidx.room.Entity

data class CompetitionResponse(
    val competitions: List<Competition>,
    val count: Int,
    val filters: Filters
)