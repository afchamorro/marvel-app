package com.acoders.marvelfanbook.features.superheroes.domain

import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.core.functional.Either
import com.acoders.marvelfanbook.core.platform.NetworkHandler
import com.acoders.marvelfanbook.core.respository.BaseRepository
import com.acoders.marvelfanbook.data.remote.api.MarvelServices
import com.acoders.marvelfanbook.data.remote.schemes.common.Paginated
import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import javax.inject.Inject

interface SuperheroesRepository {

    //TODO USAR MODELO INDEPENDIENTE PARA LA CAPA DE DOMINIO
    suspend fun superHeroesList(): Either<Failure, PaginatedWrapper<SuperheroDto>>

    class Network @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val marvelServices: MarvelServices
    ) : BaseRepository(), SuperheroesRepository {
        override suspend fun superHeroesList(): Either<Failure, PaginatedWrapper<SuperheroDto>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    {
                        marvelServices.superheroes()
                    },
                    {it},
                    PaginatedWrapper(data = Paginated(listOf(SuperheroDto.empty)) ) //TODO ESTO HAY QUE CAMBIARLO
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
