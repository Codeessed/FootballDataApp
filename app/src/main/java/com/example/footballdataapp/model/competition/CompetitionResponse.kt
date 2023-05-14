package com.example.footballdataapp.model.competition

data class CompetitionResponse(
    val competitions: List<Competition>,
    val count: Int,
    val filters: Filters
)