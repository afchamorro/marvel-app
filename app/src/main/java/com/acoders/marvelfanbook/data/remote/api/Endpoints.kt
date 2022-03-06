package com.acoders.marvelfanbook.data.remote.api

import com.acoders.marvelfanbook.data.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.data.remote.schemes.superhero.SuperheroDto
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("characters?limit=100")
    suspend fun getSuperheroes(): PaginatedWrapper<SuperheroDto>

    @GET("characters/{characterId}")
    suspend fun getSuperheroDetails(@Path("characterId") characterId: Long): PaginatedWrapper<SuperheroDto>
}
