package com.acoders.marvelfanbook.features.common.domain.models

data class Thumbnail(val path: String = "", val extension: String = "") {
    companion object {
        val empty = Thumbnail()
    }
}
