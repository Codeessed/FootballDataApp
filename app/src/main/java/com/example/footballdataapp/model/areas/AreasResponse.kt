package com.example.footballdataapp.model.areas

data class AreasResponse(
    val areas: List<AreaData>,
    val count: Int,
    val filters: Filters
)