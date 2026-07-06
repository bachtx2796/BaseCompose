package com.example.basecompose.data.remote.base

import java.io.IOException

sealed interface NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>

    data class ApiError(
        val code: Int,
        val message: String?,
        val errorBody: String?
    ) : NetworkResponse<Nothing>

    data class NetworkError(val exception: IOException) : NetworkResponse<Nothing>

    data class UnknownError(val throwable: Throwable) : NetworkResponse<Nothing>
}

fun <T> NetworkResponse<T>.getOrNull(): T? = (this as? NetworkResponse.Success)?.data
