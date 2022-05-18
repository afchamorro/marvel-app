package com.acoders.marvelfanbook.core.respository

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toError
import com.acoders.marvelfanbook.core.extensions.logE
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T, R> request(
        call: suspend () -> Response<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return try {
            val response = call.invoke()

            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> {
                    "ERROR: ${response.errorBody().toString()}".logE()
                    Either.Left(Failure.Unknown(response.code().toString()))
                }
            }

        } catch (exception: Exception) {
            "ERROR: ${exception.message.toString()}".logE()
            Either.Left(exception.toError())
        }
    }
}

