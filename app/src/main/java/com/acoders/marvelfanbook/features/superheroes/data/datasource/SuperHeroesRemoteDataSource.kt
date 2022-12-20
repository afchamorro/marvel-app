package com.acoders.marvelfanbook.features.superheroes.data.datasource

import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto

interface SuperHeroesRemoteDataSource {

    suspend fun superheroes(): PaginatedWrapper<SuperheroDto>
}
