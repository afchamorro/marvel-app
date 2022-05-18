package com.acoders.marvelfanbook.features.superheroes.domain

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.platform.NetworkHandler
import com.acoders.marvelfanbook.core.respository.BaseRepository
import com.acoders.marvelfanbook.data.remote.api.MarvelServices
import com.acoders.marvelfanbook.data.remote.schemes.common.Paginated
import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.common.Wrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import javax.inject.Inject

interface SuperheroesRepository {

    suspend fun superHeroesList(): Either<Failure, PaginatedWrapper<Superhero>>

    suspend fun superHero(id: Long): Either<Failure, Superhero>

    class Network @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val marvelServices: MarvelServices
    ) : BaseRepository(), SuperheroesRepository {
        override suspend fun superHeroesList(): Either<Failure, PaginatedWrapper<Superhero>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    {
                        marvelServices.superheroes()
                    },
                    ::getResponseAsSuperheroesList,
                    PaginatedWrapper(data = Paginated(results = listOf(SuperheroDto.empty)))
                )
                false -> Either.Left(Failure.Connectivity)
            }
        }

        private fun getResponseAsSuperheroesList(wrapper: PaginatedWrapper<SuperheroDto>): PaginatedWrapper<Superhero> {
            return PaginatedWrapper(
                attributionText = wrapper.attributionText,
                data = Paginated(
                    offset = wrapper.data.offset,
                    limit = wrapper.data.limit,
                    total = wrapper.data.total,
                    count = wrapper.data.count,
                    results = wrapper.data.results.map { it.asDomainModel() })
            )
        }

        override suspend fun superHero(id: Long): Either<Failure, Superhero> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    {
                        marvelServices.superhero(id)
                    },
                    ::getResponseAsSuperhero,
                    Wrapper(data = SuperheroDto.empty)
                )
                false -> Either.Left(Failure.Connectivity)
            }
        }

        private fun getResponseAsSuperhero(wrapper: Wrapper<SuperheroDto>): Superhero {
            wrapper.apply {
                return data.asDomainModel()
            }
        }
    }
}
