package com.acoders.marvelfanbook.features.comics.data.datasource

import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper

interface ComicsRemoteDataSource {

    suspend fun comicsByCharacter(characterId: Long): PaginatedWrapper<ComicDto>
}