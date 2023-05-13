package com.example.footballdataapp.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballdataapp.db.converter.Converters
import com.example.footballdataapp.db.dao.AreaDao
import com.example.footballdataapp.db.dao.CompetitionDao
import com.example.footballdataapp.model.Area
import com.example.footballdataapp.model.Competition

@Database(
    entities = [Area::class, Competition::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class DataDatabase: RoomDatabase() {

    abstract fun getAreaDao(): AreaDao
    abstract fun getCompetitionDao(): CompetitionDao

//    companion object{
//        @Volatile
//        private var instance: DataDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
//            instance ?: createDatabase(context).also{
//                instance = it
//            }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                DataDatabase::class.java,
//                "data_db.db"
//            ).build()
//
//    }
}