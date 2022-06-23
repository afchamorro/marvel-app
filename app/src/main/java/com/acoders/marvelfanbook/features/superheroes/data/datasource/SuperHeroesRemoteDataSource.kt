package com.acoders.marvelfanbook.features.superheroes.data.datasource

import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper

interface SuperHeroesRemoteDataSource {

    suspend fun superheroes(): PaginatedWrapper<SuperheroDto>
}
