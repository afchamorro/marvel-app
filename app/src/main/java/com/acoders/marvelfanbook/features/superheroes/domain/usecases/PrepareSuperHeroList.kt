package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.framework.remote.schemes.common.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero
import com.acoders.marvelfanbook.features.superheroes.presentation.model.SuperheroView
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PrepareSuperHeroList @Inject constructor() {

    suspend operator fun invoke(superHeroesData: PaginatedWrapper<Superhero>): List<SuperheroView> = withContext(Dispatchers.Default) {
        superHeroesData.data.results.map { it.toPresentationModel() }
    }
}

