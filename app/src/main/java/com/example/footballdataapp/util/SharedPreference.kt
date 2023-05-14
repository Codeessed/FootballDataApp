package com.example.footballdataapp.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreference {
    companion object{
        val MY_PREF = "my_pref"

        val AREAS_KEY = "areas_key"



        lateinit var preferences: SharedPreferences

        fun initSharedPreference(activity: Activity){
            preferences = activity.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        }

        fun saveAreaIds(ids: String){
            preferences.edit().putString(AREAS_KEY, ids).apply()
        }

        fun getAreaIds(): String?{
            return preferences.getString(AREAS_KEY, null)
        }

    }
}