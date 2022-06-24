package com.acoders.marvelfanbook.features.comics.framework.remote

import com.acoders.marvelfanbook.features.comics.data.datasource.ComicsRemoteDataSource
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import javax.inject.Inject

class ComicsAPIDataSource @Inject constructor(private val endpoints: MarvelEndpoints) :
    ComicsRemoteDataSource {

    override suspend fun comicsByCharacter(characterId: Long): PaginatedWrapper<ComicDto> =
        endpoints.getSuperheroComics(characterId)
}