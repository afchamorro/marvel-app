package com.acoders.marvelfanbook.framework.remote.api

import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.framework.remote.schemes.common.Wrapper
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelEndpoints {

    @GET("characters?limit=100")
    suspend fun getSuperheroes(): Response<PaginatedWrapper<SuperheroDto>>

    @GET("characters/{characterId}")
    suspend fun getSuperheroDetails(@Path("characterId") characterId: Long): Response<Wrapper<SuperheroDto>>
}
