package com.acoders.marvelfanbook.data.remote.api

import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MarvelServices @Inject constructor(retrofit: Retrofit) : MarvelRequests {

    private val endpoints = retrofit.create(MarvelEndpoints::class.java)

    override suspend fun superheroes(): Response<PaginatedWrapper<SuperheroDto>> {
        return endpoints.getSuperheroes()
    }

    override suspend fun superhero(id: Long): Response<PaginatedWrapper<SuperheroDto>> {
        return endpoints.getSuperheroDetails(id)
    }
}