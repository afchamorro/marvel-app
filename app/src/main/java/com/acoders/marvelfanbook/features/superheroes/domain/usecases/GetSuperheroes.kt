package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.functional.Either
import com.acoders.marvelfanbook.core.interactor.UseCase
import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import javax.inject.Inject

class GetSuperheroes @Inject constructor(private val repository: SuperheroesRepository) {

    operator fun invoke(): Flow<List<SuperheroView>> =
        repository.getSuperHeroesList().map { heroes -> heroes.map { it.toPresentationModel() } }
}
