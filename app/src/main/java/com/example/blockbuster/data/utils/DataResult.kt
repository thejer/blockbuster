package com.example.blockbuster.data.utils

sealed interface DataResult<out SuccessT, out FailureT> {
    val isSuccess: Boolean
    val isFailure: Boolean

    data class Success<out T>(
        val value: T,
        override val isSuccess: Boolean = true,
        override val isFailure: Boolean = false
    ) :
        DataResult<T, Nothing>

    data class Failure<out T>(
        val value: T,
        override val isSuccess: Boolean = false,
        override val isFailure: Boolean = true
    ) :
        DataResult<Nothing, T>

    companion object {
        fun <T> success(value: T): DataResult<T, Nothing> = Success(value)
        fun <T> failure(value: T): DataResult<Nothing, T> = Failure(value)
    }
}

fun <SuccessT, FailureT> DataResult<SuccessT, FailureT>.getOrThrow() = when (this) {
    is DataResult.Success -> value
    is DataResult.Failure -> throw Exception()
}

fun <SuccessT, FailureT> DataResult<SuccessT, FailureT>.failureOrThrow() = when (this) {
    is DataResult.Failure -> value
    is DataResult.Success -> throw Exception()
}

const val GENERIC_ERROR_MESSAGE = "An error has occurred"