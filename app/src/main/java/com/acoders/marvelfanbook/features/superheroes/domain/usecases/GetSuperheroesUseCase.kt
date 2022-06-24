package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSuperheroesUseCase @Inject constructor(private val repository: SuperheroesRepository) {

    //TODO, CREO QUE DEBEMOS DEVOLER EL SUPERHERO Y MAPEARLO EN EL VIEWMODEL
    operator fun invoke(): Flow<List<SuperheroView>> =
        repository.getSuperHeroesList().map { heroes -> heroes.map { it.toPresentationModel() } }
}
