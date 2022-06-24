package com.acoders.marvelfanbook.framework.remote.api

import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelEndpoints {

    @GET("characters?series=14779")
    suspend fun getSuperheroes(
        @Query("series", encoded = true) series: String,
        @Query("limit") limit: Int
    ): PaginatedWrapper<SuperheroDto>

    @GET("characters/{characterId}/comics")
    suspend fun getSuperheroComics(@Path("characterId") characterId: Long): PaginatedWrapper<ComicDto>
}
