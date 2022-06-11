package com.acoders.marvelfanbook.features.common.domain.models

import com.acoders.marvelfanbook.features.common.presentation.model.ThumbnailView

data class Thumbnail(val path: String = "", val extension: String = "") {
    companion object {
        val empty = Thumbnail()
    }

    fun toPresentationModel() : ThumbnailView = ThumbnailView(path, extension)
}
