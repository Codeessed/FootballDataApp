package com.example.footballdataapp.model.areas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "area_table")
data class AreaData(
    val countryCode: String,
    val flag: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val parentArea: String?,
    val parentAreaId: Int?
)