package com.acoders.marvelfanbook.features.superheroes.framework.remote

import com.acoders.marvelfanbook.features.common.framework.remote.ThumbnailDto
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero

data class SuperheroDto(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val thumbnail: ThumbnailDto = ThumbnailDto.empty
){
    fun asDomainModel(): Superhero{
        return Superhero(
            id,
            name,
            description,
            thumbnail.asDomainModel()
        )
    }

    companion object {
        val empty = SuperheroDto()
    }
}
