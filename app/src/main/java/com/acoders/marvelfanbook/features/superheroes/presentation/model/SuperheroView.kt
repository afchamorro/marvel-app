package com.acoders.marvelfanbook.features.superheroes.presentation.model

import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail
import com.acoders.marvelfanbook.features.common.presentation.model.ThumbnailView
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero


data class SuperheroView(
    val id: Long = 0,
    val name: String = "",
    val thumbnail: ThumbnailView = ThumbnailView.empty
) : DelegateAdapterItem {

    override fun id(): Any {
        return id
    }

    override fun content(): Any {
        return SuperheroviewContent(name, thumbnail)
    }

    inner class SuperheroviewContent(
        val name: String,
        val thumbnail: ThumbnailView
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

    fun toDomainModel(): Superhero = Superhero(id, name, thumbnail = thumbnail.toDomainModel())
}


