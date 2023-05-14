package com.example.footballdataapp.util

sealed class BoundResource<T>(
    val data: T? = null,
    val error: Throwable? = null
){
    class Success<T>(data: T): BoundResource<T>(data)
    class Loading<T>(data: T? = null): BoundResource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null): BoundResource<T>(data, throwable)
    class Empty<T>(throwable: Throwable? = null, data: T? = null): BoundResource<T>(data, throwable)
}