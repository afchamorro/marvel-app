package com.acoders.marvelfanbook.data.remote.schemes.superhero

import com.squareup.moshi.Json

data class SuperheroDto(
    @field:Json(name = "id")
    val id: Long,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "thumbnail")
    val thumbnail: ThumbnailDto,
    @field:Json(name = "comics")
    val comics: ResourceList,
    @field:Json(name = "stories")
    val stories: ResourceList,
    @field:Json(name = "events")
    val events: ResourceList,
    @field:Json(name = "series")
    val series: ResourceList
)

data class ThumbnailDto(val path: String, val extension: String)

data class ResourceList(val available: Int)
