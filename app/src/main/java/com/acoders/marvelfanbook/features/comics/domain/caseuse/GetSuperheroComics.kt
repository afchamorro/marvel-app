package com.acoders.marvelfanbook.features.comics.domain.caseuse

import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import javax.inject.Inject

class GetSuperheroComics @Inject constructor(private val repository: ComicsRepository) {

    suspend operator fun invoke(id: Long) = repository.getSuperheroComics(id)
}
