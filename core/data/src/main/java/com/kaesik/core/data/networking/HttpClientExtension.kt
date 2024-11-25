package com.kaesik.core.data.networking

import com.kaesik.core.data.BuildConfig
import com.kaesik.core.domain.util.DataError
import com.kaesik.core.domain.util.RuniqueResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified  Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): RuniqueResult<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified  Response: Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): RuniqueResult<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response: Any> HttpClient.post(
    route: String,
    body: Request
): RuniqueResult<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
            }
        }
    }

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): RuniqueResult<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return RuniqueResult.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return RuniqueResult.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return RuniqueResult.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): RuniqueResult<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> RuniqueResult.Success(response.body<T>())
        401 -> RuniqueResult.Error(DataError.Network.UNAUTHORIZED)
        408 -> RuniqueResult.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> RuniqueResult.Error(DataError.Network.CONFLICT)
        413 -> RuniqueResult.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> RuniqueResult.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500 .. 599 -> RuniqueResult.Error(DataError.Network.SERVER_ERROR)
        else -> RuniqueResult.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> BuildConfig.BASE_URL + "/" + route
    }
}
