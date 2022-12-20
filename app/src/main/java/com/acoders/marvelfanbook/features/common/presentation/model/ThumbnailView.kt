package com.acoders.marvelfanbook.features.common.presentation.model

import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail

class ThumbnailView(private val path: String = "",private val extension: String = "") {
    companion object {
        val empty = ThumbnailView()
    }

    fun getUri() : String = "$path.$extension"

    fun toDomainModel(): Thumbnail = Thumbnail(path, extension)
}