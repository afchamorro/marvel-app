package com.acoders.marvelfanbook.data.remote.api

import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.common.Wrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import retrofit2.Response
import javax.inject.Inject

class MarvelServices @Inject constructor(private val endpoints: MarvelEndpoints) : MarvelRequests {

    override suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>> {
        return endpoints.getSuperheroes()
    }

    override suspend fun superhero(id: Long): Response<Wrapper<SuperheroDto>> {
        return endpoints.getSuperheroDetails(id)
    }
}
