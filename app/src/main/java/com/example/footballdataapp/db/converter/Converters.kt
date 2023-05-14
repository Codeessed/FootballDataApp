package com.example.footballdataapp.db.converter

import androidx.room.TypeConverter
import com.example.footballdataapp.model.competition.Area
import com.example.footballdataapp.model.competition.CurrentSeason
import com.google.gson.Gson

class Converters {

    var gson = Gson()

    @TypeConverter
    fun fromArea(area: Area): String{
        return gson.toJson(area)
    }

    @TypeConverter
    fun toArea(areaString: String): Area {
        return gson.fromJson(areaString, Area::class.java)
    }


}