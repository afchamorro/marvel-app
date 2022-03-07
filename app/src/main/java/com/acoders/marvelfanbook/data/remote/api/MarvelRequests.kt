package com.acoders.marvelfanbook.data.remote.api

import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import retrofit2.Response

interface MarvelRequests {
    suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>>
}
