package com.example.blockbuster.data.remote.utils

import java.lang.Exception

sealed interface ApiResult<out SuccessT, out FailureT> {
    val isSuccess: Boolean
    val isFailure: Boolean

    data class Success<out T>(val value: T, override val isSuccess: Boolean = true, override val isFailure: Boolean = false): ApiResult<T, Nothing>

    data class Failure<out T>(val value: T, override val isSuccess: Boolean = false, override val isFailure: Boolean = true): ApiResult<Nothing, T>

    companion object {
        fun <T> success(value: T): ApiResult<T, Nothing> = Success(value)
        fun <T> failure(value: T): ApiResult<Nothing, T> = Failure(value)
    }
}

fun <SuccessT, FailureT> ApiResult<SuccessT, FailureT>.getOrThrow() = when (this) {
    is ApiResult.Success -> value
    is ApiResult.Failure -> throw Exception()
}

fun <SuccessT, FailureT> ApiResult<SuccessT, FailureT>.failureOrThrow() = when (this) {
    is ApiResult.Failure -> value
    is ApiResult.Success -> throw Exception()
}