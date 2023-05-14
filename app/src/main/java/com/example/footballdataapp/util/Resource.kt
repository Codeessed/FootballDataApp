package com.example.footballdataapp.util

import com.example.footballdataapp.model.ErrorMessage

sealed class Resource<T>(val data: T?, val error: ErrorMessage?, val message: String?){
    class Success<T>(data: T?):Resource<T>(data, null,  null)
    class Error<T>(error: ErrorMessage?):Resource<T>(null, error, null)
    class Failure<T>(message: String?): Resource<T>(null, null, message)
}
