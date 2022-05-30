package com.acoders.marvelfanbook.core.interactor

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    suspend operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        onResult(run(params))
    }

    class None
}