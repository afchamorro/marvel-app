package com.acoders.marvelfanbook.features

import com.acoders.marvelfanbook.core.platform.AppDispatcherProvider
import com.acoders.marvelfanbook.features.comics.data.repository.ComicsRepositoryImpl
import com.acoders.marvelfanbook.features.comics.domain.model.Comic
import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.comics.framework.remote.ComicsAPIDataSource
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicView
import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail
import com.acoders.marvelfanbook.features.common.framework.remote.Paginated
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.common.framework.remote.ThumbnailDto
import com.acoders.marvelfanbook.features.common.presentation.model.ThumbnailView
import com.acoders.marvelfanbook.features.superheroes.FakeAttributionInfoPreferencesDataSource
import com.acoders.marvelfanbook.features.superheroes.FakeRemoteService
import com.acoders.marvelfanbook.features.superheroes.FakeSuperheroDao
import com.acoders.marvelfanbook.features.superheroes.data.repository.SuperHeroesRepositoryImpl
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroEntity
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroesLocalDataSourceImpl
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperHeroesRemoteDataSourceImpl
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView

fun buildSuperheroesDtoWrapperSample(superheroes: List<SuperheroDto>? = null): PaginatedWrapper<SuperheroDto> {
    val result =
        if (superheroes.isNullOrEmpty()) buildSuperheroesDtoSample(1, 2, 3) else superheroes
    return PaginatedWrapper(data = Paginated(results = result))
}

fun buildSuperheroesDtoSample(vararg id: Long): List<SuperheroDto> = id.map {
    SuperheroDto(
        id = it,
        name = "Name $it",
        description = "Description $it",
        thumbnail = ThumbnailDto.empty
    )
}

fun buildSuperheroesViewSample(vararg id: Long): List<SuperheroView> = id.map {
    SuperheroView(
        id = it,
        name = "Name $it",
        description = "Description $it",
        thumbnail = ThumbnailView.empty
    )
}

fun buildComicsDtoWrapperSample(comics: List<ComicDto>? = null): PaginatedWrapper<ComicDto> {
    val result = if (comics.isNullOrEmpty()) buildComicsDtoSample(1, 2, 3) else comics
    return PaginatedWrapper(data = Paginated(results = result))
}

fun buildComicsDtoSample(vararg id: Int): List<ComicDto> = id.map {
    ComicDto(
        id = it,
        title = "Title $it",
        thumbnail = ThumbnailDto.empty
    )
}

fun buildComicsListSample(vararg id: Int): List<Comic> = id.map {
    Comic(
        id = it,
        title = "Title $it",
        thumbnail = Thumbnail.empty
    )
}

fun buildSuperheroesEntitySample(vararg id: Long): List<SuperHeroEntity> = id.map {
    SuperHeroEntity(
        id = it,
        name = "Name $it",
        description = "Description $it"
    )
}

fun buildSuperheroViewSample(id: Long): SuperheroView = SuperheroView(
    id = id,
    name = "Name $id",
    description = "Description $id"
)

fun buildComicsViewSample(vararg id: Int): List<ComicView> = id.map {
    ComicView(
        id = it,
        title = "Title $it",
        thumbnail = ThumbnailView.empty
    )
}

fun buildSuperheroesRepositoryWith(
    localDataSuperheroesData: List<SuperHeroEntity>,
    remoteSuperheroesData: List<SuperheroDto>,
    remoteComicsData: List<ComicDto>,
    link: String
): SuperheroesRepository {

    val remoteDataSource = SuperHeroesRemoteDataSourceImpl(
        FakeRemoteService(remoteSuperheroesData, remoteComicsData),
        AppDispatcherProvider()
    )
    val localDataSource = SuperHeroesLocalDataSourceImpl(
        FakeSuperheroDao(localDataSuperheroesData),
        AppDispatcherProvider()
    )
    val attributionInfoLocalDataSource = FakeAttributionInfoPreferencesDataSource(link)

    return SuperHeroesRepositoryImpl(
        remoteDataSource,
        localDataSource,
        attributionInfoLocalDataSource
    )
}

fun buildComicsRepositoryWith(
    remoteSuperheroesData: List<SuperheroDto>,
    remoteComicsData: List<ComicDto>,
): ComicsRepository {

    val remoteDataSource = ComicsAPIDataSource(
        FakeRemoteService(remoteSuperheroesData, remoteComicsData),
        AppDispatcherProvider()
    )

    return ComicsRepositoryImpl(
        remoteDataSource
    )
}