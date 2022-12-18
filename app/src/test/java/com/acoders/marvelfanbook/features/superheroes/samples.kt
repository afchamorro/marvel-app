package com.acoders.marvelfanbook.features.superheroes

import com.acoders.marvelfanbook.features.comics.domain.model.Comic
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicView
import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail
import com.acoders.marvelfanbook.features.common.presentation.model.ThumbnailView
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView

val sampleSuperHero = Superhero(
    0,
    "",
    "",
    Thumbnail.empty
)

val sampleSuperHeroView = SuperheroView(
    0,
    "",
    "",
    ThumbnailView.empty
)

val sampleComics = Comic(id = 0, title = "", thumbnail = Thumbnail.empty)

val sampleComicsView = ComicView(id = 0, title = "", thumbnail = ThumbnailView.empty)