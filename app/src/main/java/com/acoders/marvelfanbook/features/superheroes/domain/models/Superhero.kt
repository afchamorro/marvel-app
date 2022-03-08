package com.acoders.marvelfanbook.features.superheroes.domain.models

data class Superhero(
    val id: Long = 0,
    val name: String = "",
    val thumbnail: Thumbnail = Thumbnail.empty,
    val comics: ResourceList = ResourceList.empty,
    val stories: ResourceList = ResourceList.empty,
    val events: ResourceList = ResourceList.empty,
    val series: ResourceList = ResourceList.empty
)

data class Thumbnail(val path: String = "", val extension: String = "") {
    companion object {
        val empty = Thumbnail()
    }
}

data class ResourceList(val available: Int = 0) {
    companion object {
        val empty = ResourceList()
    }
}
