package com.acoders.marvelfanbook.features.superheroes.presentation.model

import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.superheroes.domain.models.Thumbnail

data class SuperheroView(
    val id: Long = 0,
    val name: String = "",
    val thumbnail: Thumbnail = Thumbnail.empty
) : DelegateAdapterItem {

    override fun id(): Any {
        return id
    }

    override fun content(): Any {
        return SuperheroviewContent(name, thumbnail)
    }

    inner class SuperheroviewContent(
        val name: String,
        val thumbnail: Thumbnail
    ) {

        override fun equals(other: Any?): Boolean {
            return other is SuperheroviewContent
                    && other.name == this.name
                    && other.thumbnail == this.thumbnail
        }

        override fun hashCode(): Int {
            return name.hashCode() + thumbnail.hashCode()
        }

    }
}