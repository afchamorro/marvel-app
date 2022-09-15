package com.acoders.marvelfanbook.features.comics.framework.model

import com.acoders.marvelfanbook.features.comics.domain.model.Comic
import com.acoders.marvelfanbook.features.common.framework.remote.ThumbnailDto

data class ComicDto(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailDto
){
    fun asDomainModel() = Comic(id, title, thumbnail.asDomainModel())
}
