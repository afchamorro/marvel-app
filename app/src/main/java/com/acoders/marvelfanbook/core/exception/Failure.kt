package com.acoders.marvelfanbook.core.exception

import retrofit2.HttpException
import java.io.IOException

sealed interface Failure {
    class Server(val code: Int) : Failure
    object Connectivity : Failure
    class Unknown(val message: String) : Failure
}

fun Throwable.toFailure(): Failure = when (this) {
    is IOException -> Failure.Connectivity
    is HttpException -> Failure.Server(code())
    else -> Failure.Unknown(message ?: "")
}
