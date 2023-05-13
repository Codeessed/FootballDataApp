package com.example.footballdataapp.db.converter

import androidx.room.TypeConverter
import com.example.footballdataapp.model.Area
import com.example.footballdataapp.model.CurrentSeason
import com.google.gson.Gson

class Converters {

    var gson = Gson()

    @TypeConverter
    fun fromArea(area: Area): String{
        return gson.toJson(area)
    }

    @TypeConverter
    fun toArea(areaString: String): Area{
        return gson.fromJson(areaString, Area::class.java)
    }

    @TypeConverter
    fun fromCurrentSeason(currentSeason: CurrentSeason): String{
        return gson.toJson(currentSeason)
    }

    @TypeConverter
    fun toCurrentSeason(seasonString: String): CurrentSeason{
        return gson.fromJson(seasonString, CurrentSeason::class.java)
    }

}