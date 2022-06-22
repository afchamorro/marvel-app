package com.acoders.marvelfanbook.framework.remote.api

import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.framework.remote.schemes.common.Wrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelEndpoints {

    @GET("characters?series=14779")
    suspend fun getSuperheroes(
        @Query("series", encoded = true) series: String,
        @Query("limit") limit: Int
    ): Response<PaginatedWrapper<SuperheroDto>>

    @GET("characters/{characterId}")
    suspend fun getSuperheroDetails(@Path("characterId") characterId: Long): Response<Wrapper<SuperheroDto>>
}
