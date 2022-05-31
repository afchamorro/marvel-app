package com.acoders.marvelfanbook.features.superheroes.domain.repository

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero

interface SuperheroesRepository {

    suspend fun superHeroesList(): Either<Failure, PaginatedWrapper<Superhero>>

    suspend fun superHero(id: Long): Either<Failure, Superhero>
}
