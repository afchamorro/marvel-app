package com.acoders.marvelfanbook.data.remote.schemes.common

import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail

data class ThumbnailDto(val path: String = "", val extension: String = "") {

    fun asDomainModel(): Thumbnail = Thumbnail(path, extension)

    companion object {
        val empty = ThumbnailDto()
    }
}