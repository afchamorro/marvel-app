package com.acoders.marvelfanbook.features.superheroes.framework.remote

import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import retrofit2.Response
import javax.inject.Inject

class SuperHeroesRemoteDataSourceImpl @Inject constructor(private val endpoints: MarvelEndpoints) : SuperHeroesRemoteDataSource {

    override suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>> {
        return endpoints.getSuperheroes()
    }
}
