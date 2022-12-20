package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import javax.inject.Inject

class FetchHeroesListUseCase @Inject constructor(private val heroesRepository: SuperheroesRepository) {

    suspend operator fun invoke(): Failure? = heroesRepository.fetchHeroesList()
}