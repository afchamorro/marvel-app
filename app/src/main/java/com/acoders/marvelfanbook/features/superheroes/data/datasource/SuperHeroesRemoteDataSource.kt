package com.acoders.marvelfanbook.features.superheroes.data.datasource

import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.framework.remote.schemes.common.Wrapper
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import retrofit2.Response

interface SuperHeroesRemoteDataSource {

    suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>>

    suspend fun superhero(id: Long): Response<Wrapper<SuperheroDto>>
}
