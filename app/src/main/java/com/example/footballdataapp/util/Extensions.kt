package com.example.footballdataapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.fromIsoToString(incomingPattern: String, outgoingPattern: String): String? {
    return try {
        this?.let { stringDate ->
            SimpleDateFormat(incomingPattern, Locale.getDefault()).parse(stringDate)?.let { isoDate ->
                SimpleDateFormat(outgoingPattern, Locale.getDefault()).format(isoDate)
            }
        }
    }catch (ex: ParseException){
        null
    }
}