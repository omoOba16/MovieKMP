package com.example.moviekmp.core.data

import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): NetworkResult<T, DataError.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return NetworkResult.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: UnresolvedAddressException) {
        return NetworkResult.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return NetworkResult.Error(DataError.Remote.UNKNOWN)
    }
    return responseToResult(response)
}


suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): NetworkResult<T, DataError.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                NetworkResult.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                NetworkResult.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> NetworkResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> NetworkResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> NetworkResult.Error(DataError.Remote.SERVER)
        else -> NetworkResult.Error(DataError.Remote.UNKNOWN)
    }
}