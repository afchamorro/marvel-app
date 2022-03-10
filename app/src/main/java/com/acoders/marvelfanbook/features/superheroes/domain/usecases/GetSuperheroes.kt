package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.functional.Either
import com.acoders.marvelfanbook.core.interactor.UseCase
import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import javax.inject.Inject

class GetSuperheroes @Inject constructor(private val repository: SuperheroesRepository) :
    UseCase<PaginatedWrapper<Superhero>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, PaginatedWrapper<Superhero>> {
        return repository.superHeroesList()
    }
}
