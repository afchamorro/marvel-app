package com.acoders.marvelfanbook.data.remote.schemes.superhero

import com.squareup.moshi.Json

data class SuperheroDto(
    @field:Json(name = "id")
    val id: Long = 0,
    @field:Json(name = "name")
    val name: String = "",
    @field:Json(name = "thumbnail")
    val thumbnail: ThumbnailDto = ThumbnailDto.empty,
    @field:Json(name = "comics")
    val comics: ResourceList = ResourceList.empty,
    @field:Json(name = "stories")
    val stories: ResourceList = ResourceList.empty,
    @field:Json(name = "events")
    val events: ResourceList = ResourceList.empty,
    @field:Json(name = "series")
    val series: ResourceList = ResourceList.empty
) {
    companion object {
        val empty = SuperheroDto()
    }
}

data class ThumbnailDto(val path: String = "", val extension: String = "") {
    companion object {
        val empty = ThumbnailDto()
    }
}

data class ResourceList(val available: Int = 0) {
    companion object {
        val empty = ResourceList()
    }
}

