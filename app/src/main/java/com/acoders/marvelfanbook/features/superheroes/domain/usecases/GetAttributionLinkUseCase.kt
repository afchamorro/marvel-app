package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAttributionLinkUseCase  @Inject constructor(private val repository: SuperheroesRepository) {
    operator fun invoke(): Flow<String> = repository.getAttributionLink()
}
