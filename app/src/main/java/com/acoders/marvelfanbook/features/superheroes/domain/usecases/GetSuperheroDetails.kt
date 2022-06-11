package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.interactor.UseCase
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import javax.inject.Inject

class GetSuperheroDetails @Inject constructor(private val repository: SuperheroesRepository) :
    UseCase<Superhero, GetSuperheroDetails.Params>() {

    override suspend fun run(params: Params): Either<Failure, Superhero> {
        return repository.superHero(params.id)
    }

    data class Params(val id: Long)
}
