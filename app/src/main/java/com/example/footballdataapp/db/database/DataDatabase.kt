package com.example.footballdataapp.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballdataapp.db.converter.Converters
import com.example.footballdataapp.db.dao.AreaDao
import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.model.competition.Competition

@Database(
    entities = [AreaData::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class DataDatabase: RoomDatabase() {

    abstract fun getAreaDao(): AreaDao
}