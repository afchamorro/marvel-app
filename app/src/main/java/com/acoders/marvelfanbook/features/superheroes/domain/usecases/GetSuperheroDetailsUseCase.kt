package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSuperheroDetailsUseCase @Inject constructor(private val repository: SuperheroesRepository) {

    operator fun invoke(id: Long) = repository.superHero(id).map { it.toPresentationModel() }

}