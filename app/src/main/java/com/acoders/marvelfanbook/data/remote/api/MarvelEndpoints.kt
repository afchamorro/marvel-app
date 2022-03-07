package com.acoders.marvelfanbook.data.remote.api

import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelEndpoints {

    @GET("characters?limit=100")
    suspend fun getSuperheroes(): Response<PaginatedWrapper<SuperheroDto>>

    @GET("characters/{characterId}")
    suspend fun getSuperheroDetails(@Path("characterId") characterId: Long): Response<PaginatedWrapper<SuperheroDto>>
}
