package com.acoders.marvelfanbook.features.comics.data.repository

import arrow.core.Either
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.comics.data.datasource.ComicsRemoteDataSource
import com.acoders.marvelfanbook.features.comics.domain.model.Comic
import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(private val comicsRemoteDataSource: ComicsRemoteDataSource) :
    ComicsRepository {

    override suspend fun getSuperheroComics(superheroId: Long): Either<Failure, List<Comic>> =
        comicsRemoteDataSource.comicsByCharacter(superheroId)
            .map { it.data.results.map { comicDto -> comicDto.asDomainModel() } }
}