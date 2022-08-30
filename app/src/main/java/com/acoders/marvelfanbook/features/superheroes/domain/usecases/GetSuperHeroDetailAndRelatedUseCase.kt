package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.core.platform.delegateadapter.DelegateAdapterItem
import com.acoders.marvelfanbook.features.comics.domain.caseuse.GetSuperheroComicsUseCase
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSetView
import com.acoders.marvelfanbook.features.superheroes.presentation.model.DescriptionView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSuperHeroDetailAndRelatedUseCase @Inject constructor(
    private val getSuperheroDetailsUseCase: GetSuperheroDetailsUseCase,
    private val getSuperheroComicsUseCase: GetSuperheroComicsUseCase
) {

    suspend operator fun invoke(id: Long): Flow<List<DelegateAdapterItem>> = flow {

        var descriptionView = DescriptionView("")
        var comicSetView: ComicSetView = ComicSetView.emptySkeleton

        emit(listOf(descriptionView, comicSetView))

        getSuperheroComicsUseCase(id).fold({
            throw Exception(it.toString())
        }, { comics ->
            comicSetView = ComicSetView(comics.map { it.toPresentationModel() })
            emit(listOf(descriptionView, comicSetView))
        })

        getSuperheroDetailsUseCase(id).collect {
            descriptionView = it.toDescriptionView()
            emit(listOf(descriptionView, comicSetView))
        }
    }
}
