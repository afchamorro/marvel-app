package com.acoders.marvelfanbook.features.comics.framework.remote

import com.acoders.marvelfanbook.core.platform.DispatcherProvider
import com.acoders.marvelfanbook.features.comics.data.datasource.ComicsRemoteDataSource
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicsAPIDataSource @Inject constructor(
    private val endpoints: MarvelEndpoints,
    private val appDispatcherProvider: DispatcherProvider
) : ComicsRemoteDataSource {

    override suspend fun comicsByCharacter(characterId: Long): PaginatedWrapper<ComicDto> = withContext(appDispatcherProvider.io) {
        endpoints.getSuperheroComics(characterId)
    }
}