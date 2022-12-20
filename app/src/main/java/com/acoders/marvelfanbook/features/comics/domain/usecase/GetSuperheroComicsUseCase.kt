package com.acoders.marvelfanbook.features.comics.domain.usecase

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicView
import javax.inject.Inject

class GetSuperheroComicsUseCase @Inject constructor(private val repository: ComicsRepository) {

    suspend operator fun invoke(id: Long): Either<Failure, List<ComicView>> =
        repository.getSuperheroComics(id).map { it.map { comic ->  comic.toPresentationModel() } }
}

