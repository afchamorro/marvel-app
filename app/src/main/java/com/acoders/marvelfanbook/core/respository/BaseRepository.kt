package com.acoders.marvelfanbook.core.respository

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.extensions.logE
import com.acoders.marvelfanbook.core.functional.Either
import retrofit2.Response
import java.net.HttpURLConnection

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
                    return when (response.code()) {
                        HttpURLConnection.HTTP_FORBIDDEN -> Either.Left(Failure.Forbidden)
                        HTTP_TOO_MANY_CONNECTIONS -> Either.Left(Failure.ToManyConnections)
                        HttpURLConnection.HTTP_UNAUTHORIZED -> Either.Left(Failure.Unauthorized)
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> Either.Left(Failure.ServerError)
                        HttpURLConnection.HTTP_NOT_ACCEPTABLE -> Either.Left(Failure.NotAcceptable)
                        HttpURLConnection.HTTP_NOT_FOUND -> Either.Right(transform(default))
                        else -> Either.Left(Failure.ServerError)
                    }
                }
            }

        } catch (exception: Exception) {
            "ERROR: ${exception.message.toString()}".logE()
            Either.Left(Failure.GeneralError)
        }
    }

    companion object {
        const val HTTP_TOO_MANY_CONNECTIONS = 429
    }
}
