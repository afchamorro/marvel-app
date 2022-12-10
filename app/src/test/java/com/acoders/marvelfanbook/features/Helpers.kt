package com.acoders.marvelfanbook.features

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

fun buildSuperheroesDtoSample(vararg id: Long): List<SuperheroDto> = id.map {
    SuperheroDto(
        id = it,
        name = "Name $it",
        description = "Description $it",
        thumbnail = ThumbnailDto.empty
    )
}

fun buildSuperheroesDtoWrapperSample() =
    PaginatedWrapper(data = Paginated(results = buildSuperheroesDtoSample(1, 2, 3)))