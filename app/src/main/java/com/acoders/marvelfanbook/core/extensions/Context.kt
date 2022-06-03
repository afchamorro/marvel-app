package com.acoders.marvelfanbook.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

suspend fun <T, R> request(
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
        Either.Left(exception.toFailure())
    }
}

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}