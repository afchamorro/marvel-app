package com.acoders.marvelfanbook.features.superheroes.framework.remote

import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import javax.inject.Inject

class SuperHeroesRemoteDataSourceImpl @Inject constructor(private val endpoints: MarvelEndpoints) :
    SuperHeroesRemoteDataSource {

    override suspend fun superheroes(): PaginatedWrapper<SuperheroDto> {
        return endpoints.getSuperheroes(
            series = arrayListOf(
                27631,
                29061,
                15331,
                9790,
                17318,
                13896,
                20443,
                16449,
                2984
            ).joinToString(separator = ","), limit = 100
        )
    }
}
