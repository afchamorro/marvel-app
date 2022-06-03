package com.acoders.marvelfanbook.features.superheroes.framework.database

import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SuperHeroesLocalDataSourceImpl @Inject constructor(private val superHeroDao: SuperHeroDao) : SuperHeroesLocalDataSource {

    override fun getSuperHeroesList(): Flow<List<Superhero>> = superHeroDao.getSuperHeroesList().map { it.toDomainModel() }

    override suspend fun save(heroesList: List<Superhero>) {
        superHeroDao.saveSuperHeroes(heroesList.toEntityModel())
    }

    override suspend fun isEmpty(): Boolean = superHeroDao.numHeroes() == 0
}

fun List<SuperHeroEntity>.toDomainModel(): List<Superhero> = map { it.toDomainModel() }

fun List<Superhero>.toEntityModel(): List<SuperHeroEntity> = map { it.toEntityModel() }