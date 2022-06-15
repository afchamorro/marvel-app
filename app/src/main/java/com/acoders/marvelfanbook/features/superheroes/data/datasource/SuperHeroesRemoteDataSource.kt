package com.acoders.marvelfanbook.features.superheroes.data.datasource

import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import retrofit2.Response

interface SuperHeroesRemoteDataSource {

    suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>>
}
