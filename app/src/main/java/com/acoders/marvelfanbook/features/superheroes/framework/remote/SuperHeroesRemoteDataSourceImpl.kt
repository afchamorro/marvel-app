package com.acoders.marvelfanbook.features.superheroes.framework.remote

import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.framework.remote.schemes.common.Wrapper
import retrofit2.Response
import javax.inject.Inject

class SuperHeroesRemoteDataSourceImpl @Inject constructor(private val endpoints: MarvelEndpoints) : SuperHeroesRemoteDataSource {

    override suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>> {
        return endpoints.getSuperheroes()
    }

    override suspend fun superhero(id: Long): Response<Wrapper<SuperheroDto>> {
        return endpoints.getSuperheroDetails(id)
    }
}
