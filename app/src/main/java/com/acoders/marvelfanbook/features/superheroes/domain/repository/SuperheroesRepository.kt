package com.acoders.marvelfanbook.features.superheroes.domain.repository

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import kotlinx.coroutines.flow.Flow

interface SuperheroesRepository {

    fun getSuperHeroesList() : Flow<List<Superhero>>

    suspend fun fetchHeroesList(): Failure?

    suspend fun superHero(id: Long): Either<Failure, Superhero>
}
