package com.kaesik.core.domain.util

sealed interface RuniqueResult<out D, out E: Error> {
    data class Success<out D>(val data: D) : RuniqueResult<D, Nothing>
    data class Error<out E: com.kaesik.core.domain.util.Error>(val error: E) : RuniqueResult<Nothing, E>
}

inline fun <T, E: Error, R> RuniqueResult<T, E>.map(map: (T) -> R): RuniqueResult<R, E> {
    return when(this) {
        is RuniqueResult.Error -> RuniqueResult.Error(error)
        is RuniqueResult.Success -> RuniqueResult.Success(map(data))
    }
}

fun <T, E: Error> RuniqueResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

typealias EmptyResult<E> = RuniqueResult<Unit, E>
