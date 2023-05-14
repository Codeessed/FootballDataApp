package com.example.footballdataapp.util

sealed class Resource<T>(val data: T?, val error: Throwable? = null){
    class Success<T>(data: T):Resource<T>(data, null)
    class Error<T>(throwable: Throwable, data: T? = null):Resource<T>(data, throwable)
    class Loading<T>(data: T? = null):Resource<T>(data, null)

}
