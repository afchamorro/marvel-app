package com.acoders.marvelfanbook.features.comics.data.datasource

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper

interface ComicsRemoteDataSource {

    suspend fun comicsByCharacter(characterId: Long): Either<Failure, PaginatedWrapper<ComicDto>>
}