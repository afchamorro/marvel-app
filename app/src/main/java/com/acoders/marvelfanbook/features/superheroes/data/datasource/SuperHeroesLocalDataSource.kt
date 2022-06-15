package com.acoders.marvelfanbook.features.superheroes.data.datasource

import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import kotlinx.coroutines.flow.Flow

interface SuperHeroesLocalDataSource {

    fun getSuperHeroesList(): Flow<List<Superhero>>

    suspend fun save(heroesList: List<Superhero>)

    suspend fun isEmpty(): Boolean
}