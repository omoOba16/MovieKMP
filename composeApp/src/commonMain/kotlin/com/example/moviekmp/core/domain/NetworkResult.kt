package com.example.moviekmp.core.domain

/*sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>()
}*/

sealed interface NetworkResult<out D, out E> {
    data class Success<out D>(val data: D): NetworkResult<D, Nothing>
    data class Error<out E:com.example.moviekmp.core.domain.Error>(val error: E): NetworkResult<Nothing, E>
}

/*sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error<T>(val message: DataError) : NetworkResult<T>
    object Loading : NetworkResult<Nothing>
}*/