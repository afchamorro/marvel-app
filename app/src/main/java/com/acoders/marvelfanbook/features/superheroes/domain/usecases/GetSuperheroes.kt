package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSuperheroes @Inject constructor(private val repository: SuperheroesRepository) {

     operator fun invoke(): Flow<Either<Failure, PaginatedWrapper<Superhero>>> = flow {
         emit(repository.superHeroesList())
    }
}
