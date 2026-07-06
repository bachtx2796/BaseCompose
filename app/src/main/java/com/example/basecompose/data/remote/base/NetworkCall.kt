package com.example.basecompose.data.remote.base

import java.io.IOException
import retrofit2.HttpException

suspend inline fun <T> networkCall(
    crossinline execute: suspend () -> T
): NetworkResponse<T> {
    return try {
        NetworkResponse.Success(execute())
    } catch (exception: IOException) {
        NetworkResponse.NetworkError(exception)
    } catch (exception: HttpException) {
        NetworkResponse.ApiError(
            code = exception.code(),
            message = exception.message(),
            errorBody = exception.response()?.errorBody()?.string()
        )
    } catch (throwable: Throwable) {
        NetworkResponse.UnknownError(throwable)
    }
}
