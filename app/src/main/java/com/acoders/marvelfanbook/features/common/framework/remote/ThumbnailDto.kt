package com.acoders.marvelfanbook.features.common.framework.remote

import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail

data class ThumbnailDto(val path: String = "", val extension: String = "") {

    fun asDomainModel(): Thumbnail = Thumbnail(path, extension)

    companion object {
        val empty = ThumbnailDto()
    }
}