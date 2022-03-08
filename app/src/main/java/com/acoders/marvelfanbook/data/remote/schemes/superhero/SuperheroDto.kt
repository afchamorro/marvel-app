package com.acoders.marvelfanbook.data.remote.schemes.superhero

import com.acoders.marvelfanbook.features.superheroes.domain.models.ResourceList
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.domain.models.Thumbnail

data class SuperheroDto(
    val id: Long = 0,
    val name: String = "",
    val thumbnail: ThumbnailDto = ThumbnailDto.empty,
    val comics: ResourceListDto = ResourceListDto.empty,
    val stories: ResourceListDto = ResourceListDto.empty,
    val events: ResourceListDto = ResourceListDto.empty,
    val series: ResourceListDto = ResourceListDto.empty
){
    fun asDomainModel(): Superhero{
        return Superhero(
            id,
            name,
            thumbnail.asDomainModel(),
            comics.asDomainModel(),
            stories.asDomainModel(),
            events.asDomainModel(),
            series.asDomainModel()
        )
    }

    companion object {
        val empty = SuperheroDto()
    }
}

data class ThumbnailDto(val path: String = "", val extension: String = "") {

    fun asDomainModel(): Thumbnail  = Thumbnail(path, extension)

    companion object {
        val empty = ThumbnailDto()
    }
}

data class ResourceListDto(val available: Int = 0) {

    fun asDomainModel(): ResourceList = ResourceList(available)

    companion object {
        val empty = ResourceListDto()
    }
}
