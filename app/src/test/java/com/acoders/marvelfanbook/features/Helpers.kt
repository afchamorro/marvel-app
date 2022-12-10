package com.acoders.marvelfanbook.features

import com.acoders.marvelfanbook.features.comics.domain.model.Comic
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail
import com.acoders.marvelfanbook.features.common.framework.remote.Paginated
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.common.framework.remote.ThumbnailDto
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto

fun buildSuperheroDtoSample(id: Long): SuperheroDto = id.let {
    SuperheroDto(
        id = it,
        name = "Name $it",
        description = "Description $it",
        thumbnail = ThumbnailDto.empty
    )
}

fun buildSuperheroesDtoWrapperSample() =
    PaginatedWrapper(data = Paginated(results = buildSuperheroesDtoSample(1, 2, 3)))

fun buildSuperheroesDtoSample(vararg id: Long): List<SuperheroDto> = id.map {
    SuperheroDto(
        id = it,
        name = "Name $it",
        description = "Description $it",
        thumbnail = ThumbnailDto.empty
    )
}

fun buildComicsDtoWrapperSample() =
    PaginatedWrapper(data = Paginated(results = buildComicsDtoSample(1, 2, 3)))

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