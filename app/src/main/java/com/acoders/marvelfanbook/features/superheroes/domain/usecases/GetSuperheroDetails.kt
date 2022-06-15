package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import javax.inject.Inject

class GetSuperheroDetails @Inject constructor(private val repository: SuperheroesRepository){

    operator fun invoke(id: Long) = repository.superHero(id)
}