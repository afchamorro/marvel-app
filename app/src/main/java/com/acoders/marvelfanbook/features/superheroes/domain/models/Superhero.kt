package com.acoders.marvelfanbook.features.superheroes.domain.models

import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroEntity
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView

data class Superhero(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val thumbnail: Thumbnail = Thumbnail.empty
) {

    fun toPresentationModel() = SuperheroView(id, name, description, thumbnail.toPresentationModel())

    fun toEntityModel() = SuperHeroEntity(id, name, description, thumbnail.path, thumbnail.extension)
}
