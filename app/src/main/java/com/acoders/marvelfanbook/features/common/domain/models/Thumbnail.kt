package com.acoders.marvelfanbook.features.common.domain.models

import com.acoders.marvelfanbook.features.common.presentation.model.ThumbnailView

data class Thumbnail(val path: String = "", val extension: String = "") {

    fun toPresentationModel() : ThumbnailView = ThumbnailView(path, extension)

    companion object {
        val empty = Thumbnail()
    }
}
