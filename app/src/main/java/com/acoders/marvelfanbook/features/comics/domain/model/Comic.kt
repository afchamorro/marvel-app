package com.acoders.marvelfanbook.features.comics.domain.model

import com.acoders.marvelfanbook.features.comics.presentation.model.ComicView
import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail

data class Comic(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail
) {
    fun toPresentationModel() = ComicView(id, title, thumbnail.toPresentationModel())
}