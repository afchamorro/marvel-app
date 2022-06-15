package com.acoders.marvelfanbook.features.superheroes.domain.repository

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import kotlinx.coroutines.flow.Flow

interface SuperheroesRepository {

    fun getSuperHeroesList() : Flow<List<Superhero>>

    suspend fun fetchHeroesList(): Failure?

    fun superHero(id: Long): Flow<Superhero>
}
