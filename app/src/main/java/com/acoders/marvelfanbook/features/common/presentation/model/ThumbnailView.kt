package com.acoders.marvelfanbook.features.common.presentation.model

class ThumbnailView(val path: String = "", val extension: String = "") {
    companion object {
        val empty = ThumbnailView()
    }
}