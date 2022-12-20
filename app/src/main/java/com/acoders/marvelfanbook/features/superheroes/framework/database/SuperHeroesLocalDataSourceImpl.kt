package com.acoders.marvelfanbook.features.superheroes.framework.database

import com.acoders.marvelfanbook.core.platform.DispatcherProvider
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuperHeroesLocalDataSourceImpl @Inject constructor(
    private val superHeroDao: SuperHeroDao,
    private val dispatcherProvider: DispatcherProvider
) :
    SuperHeroesLocalDataSource {

    override val superheroes: Flow<List<Superhero>> =
        superHeroDao.getSuperHeroesList().map { it.toDomainModel() }

    override fun getSuperHeroesById(id: Long): Flow<Superhero> =
        superHeroDao.getSuperHeroesById(id).flowOn(dispatcherProvider.io).map { it.toDomainModel() }
            .flowOn(dispatcherProvider.default)

    override suspend fun save(heroesList: List<Superhero>) = withContext(dispatcherProvider.io) {
        superHeroDao.saveSuperHeroes(heroesList.toEntityModel())
    }

    override suspend fun isEmpty(): Boolean =
        withContext(dispatcherProvider.io) { superHeroDao.numHeroes() == 0 }
}

fun List<SuperHeroEntity>.toDomainModel(): List<Superhero> = map { it.toDomainModel() }

fun List<Superhero>.toEntityModel(): List<SuperHeroEntity> = map { it.toEntityModel() }