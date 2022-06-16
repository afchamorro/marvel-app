package com.acoders.marvelfanbook.core.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.exception.toFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

fun <T, U> Flow<T>.diff(lifecycleOwner: LifecycleOwner, mapFlow: (T) -> U, body: (U) -> Unit) {

    lifecycleOwner.launchAndCollect(
        flow = map(mapFlow).distinctUntilChanged(),
        body = body
    )
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

suspend fun <T> tryCall(action: suspend () -> T): Either<Failure, T> = try {
    action().right()
} catch (e: Exception) {
    e.toFailure().left()
}