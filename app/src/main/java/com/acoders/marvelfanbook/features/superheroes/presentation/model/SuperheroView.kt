package com.acoders.marvelfanbook.features.superheroes.presentation.model

import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail

data class SuperheroView(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val thumbnail: Thumbnail = Thumbnail.empty
)
