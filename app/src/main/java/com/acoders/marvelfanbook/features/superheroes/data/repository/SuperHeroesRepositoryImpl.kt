package com.acoders.marvelfanbook.features.superheroes.data.repository

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.extensions.tryCall
import com.acoders.marvelfanbook.features.common.framework.remote.Paginated
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.data.datasource.AttributionInfoLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SuperHeroesRepositoryImpl @Inject constructor(
    private val remoteDataSource: SuperHeroesRemoteDataSource,
    private val localDataSource: SuperHeroesLocalDataSource,
    private val attributionInfoLocalDataSource: AttributionInfoLocalDataSource
) : SuperheroesRepository {

    override fun getSuperHeroesList(): Flow<List<Superhero>> = localDataSource.getSuperHeroesList().onEach {
        if(it.isEmpty()) fetchHeroesList()
    }

    override suspend fun fetchHeroesList(): Failure? {
        if (!localDataSource.isEmpty()) return null

        val result = tryCall {
            remoteDataSource.superheroes().getResponseAsSuperheroesList()
        }

        result.fold({
            return it
        }, {
            localDataSource.save(it.data.results)
            attributionInfoLocalDataSource.saveAttributionLink(it.attributionHTML)
            return null
        })
    }

    private fun PaginatedWrapper<SuperheroDto>.getResponseAsSuperheroesList(): PaginatedWrapper<Superhero> {
        return PaginatedWrapper(
            attributionHTML = attributionHTML,
            data = Paginated(
                offset = data.offset,
                limit = data.limit,
                total = data.total,
                count = data.count,
                results = data.results.map { it.asDomainModel() })
        )
    }

    override fun superHero(id: Long): Flow<Superhero> {
        return localDataSource.getSuperHeroesById(id)
    }

    override fun getAttributionLink(): Flow<String> =
        attributionInfoLocalDataSource.getAttributionLink()
}