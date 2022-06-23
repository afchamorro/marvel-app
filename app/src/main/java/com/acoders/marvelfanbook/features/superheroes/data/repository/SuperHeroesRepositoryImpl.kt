package com.acoders.marvelfanbook.features.superheroes.data.repository

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.platform.NetworkHandler
import com.acoders.marvelfanbook.core.respository.BaseRepository
import com.acoders.marvelfanbook.features.superheroes.data.datasource.AttributionInfoLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.framework.remote.schemes.common.Paginated
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuperHeroesRepositoryImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val remoteDataSource: SuperHeroesRemoteDataSource,
    private val localDataSource: SuperHeroesLocalDataSource,
    private val attributionInfoLocalDataSource: AttributionInfoLocalDataSource
) : BaseRepository(), SuperheroesRepository {

    override fun getSuperHeroesList(): Flow<List<Superhero>> = localDataSource.getSuperHeroesList()

    override suspend fun fetchHeroesList(): Failure? {
        if (!localDataSource.isEmpty()) return null

        return when (networkHandler.isNetworkAvailable()) {
            true -> request(
                {
                    remoteDataSource.superheroes()
                },
                ::getResponseAsSuperheroesList,
                PaginatedWrapper(data = Paginated(results = listOf(SuperheroDto.empty)))
            )
            false -> Either.Left(Failure.Connectivity)
        }.fold({
            it
        }, {
            localDataSource.save(it.data.results)
            attributionInfoLocalDataSource.saveAttributionLink(it.attributionHTML)
            null
        })
    }

    private fun getResponseAsSuperheroesList(wrapper: PaginatedWrapper<SuperheroDto>): PaginatedWrapper<Superhero> {
        return PaginatedWrapper(
            attributionHTML = wrapper.attributionHTML,
            data = Paginated(
                offset = wrapper.data.offset,
                limit = wrapper.data.limit,
                total = wrapper.data.total,
                count = wrapper.data.count,
                results = wrapper.data.results.map { it.asDomainModel() })
        )
    }

    override fun superHero(id: Long): Flow<Superhero> {
        return localDataSource.getSuperHeroesById(id)
    }

    override fun getAttributionLink(): Flow<String> =
        attributionInfoLocalDataSource.getAttributionLink()

}