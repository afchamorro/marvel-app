package com.acoders.marvelfanbook.features.superheroes.framework.remote

import com.acoders.marvelfanbook.core.platform.DispatcherProvider
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuperHeroesRemoteDataSourceImpl @Inject constructor(
    private val endpoints: MarvelEndpoints,
    private val dispatcherProvider: DispatcherProvider
) :
    SuperHeroesRemoteDataSource {

    override suspend fun superheroes(): PaginatedWrapper<SuperheroDto> = withContext(dispatcherProvider.io) {
        endpoints.getSuperheroes(
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
