package com.acoders.marvelfanbook.features.common.presentation.model

import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail

class ThumbnailView(val path: String = "", val extension: String = "") {
    companion object {
        val empty = ThumbnailView()
    }

    fun toDomainModel(): Thumbnail = Thumbnail(path, extension)
}