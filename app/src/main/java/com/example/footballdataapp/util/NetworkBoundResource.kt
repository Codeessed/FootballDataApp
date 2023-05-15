package com.example.footballdataapp.util

import kotlinx.coroutines.flow.*
import java.io.IOException

inline fun<ResultType, RequestType> networkBoundResource (
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend  () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = {true}
) = flow {
    val data = query().first()
    val result = if (shouldFetch(data)){
        emit(BoundResource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map {
                BoundResource.Success(it)
            }
        }catch (throwable: Throwable){
            query().map {
                BoundResource.Error(throwable, it)
            }
        }
    }else{
        query().map { BoundResource.Success(it) }
    }
    emitAll(result)
}