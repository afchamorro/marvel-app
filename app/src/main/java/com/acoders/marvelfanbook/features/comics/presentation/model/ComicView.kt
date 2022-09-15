package com.acoders.marvelfanbook.features.comics.presentation.model

import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.common.presentation.model.ThumbnailView

data class ComicView(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailView
) : DelegateAdapterItem {
    override fun id() = id

    override fun content() = ComicViewContent(title, thumbnail)

    inner class ComicViewContent(
        val title: String,
        val thumbnail: ThumbnailView
    ) {

        override fun equals(other: Any?): Boolean {
            return other is ComicViewContent
                    && other.title == this.title
                    && other.thumbnail == this.thumbnail
        }

        override fun hashCode(): Int {
            return title.hashCode() + thumbnail.hashCode()
        }
    }
}
