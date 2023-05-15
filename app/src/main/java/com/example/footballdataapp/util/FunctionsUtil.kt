package com.example.footballdataapp.util

object FunctionsUtil {

    /**
     * the input returns an empty string if...
     *...the inputDate is empty
     *...the inputFormat is empty
     *...the outputFormat is empty
     * ...the inputDate/inputFormat is invalid
     */
    fun validateDateInput(
        inputDate: String?,
        inputFormat: String,
        outputFormat: String
    ): String{
        return inputDate.fromIsoToString(inputFormat, outputFormat)?:"..."
    }

}