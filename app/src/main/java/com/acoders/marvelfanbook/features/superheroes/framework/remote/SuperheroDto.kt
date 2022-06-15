package com.acoders.marvelfanbook.features.superheroes.framework.remote

import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.framework.remote.schemes.common.ThumbnailDto

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
