package com.acoders.marvelfanbook.features.comics.domain.repository

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.comics.domain.model.Comic

interface ComicsRepository {

    suspend fun getSuperheroComics(superheroId: Long): Either<Failure, List<Comic>>
}