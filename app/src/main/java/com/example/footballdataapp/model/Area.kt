package com.example.footballdataapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "area_table")
data class Area(
    val code: String,
    val flag: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String
)