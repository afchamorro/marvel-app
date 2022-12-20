package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.acoders.marvelfanbook.features.buildComicsRepositoryWith
import com.acoders.marvelfanbook.features.buildSuperheroesDtoSample
import com.acoders.marvelfanbook.features.buildSuperheroesEntitySample
import com.acoders.marvelfanbook.features.buildSuperheroesRepositoryWith
import com.acoders.marvelfanbook.features.comics.domain.usecase.GetSuperheroComicsUseCase
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicView
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetailsUseCase
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroEntity
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesDetailViewModel.UiState
import com.acoders.marvelfanbook.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SuperheroDetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from local source when available`() = runTest {

        val localSuperheroData = buildSuperheroesEntitySample(1, 2, 3)
        val remoteSuperheroData = buildSuperheroesDtoSample(1, 2, 3)

        val vm = buildViewModelWith(
            localSuperheroData = localSuperheroData,
            remoteSuperheroData = remoteSuperheroData
        )

        vm.heroId = 1

        vm.loadSuperheroDetail()

        vm.uiState.test {
            assertEquals(UiState(loading = true), awaitItem())
            awaitItem() //skip comics update
            val superheroView = awaitItem().superheroView!!
            assertEquals(1, superheroView.id)
            assertEquals("Name 1", superheroView.name)
            assertEquals("Description 1", superheroView.description)
            cancel()
        }
    }

    @Test
    fun `data comics is loaded from server when screen init`() = runTest {

        val localSuperheroData = buildSuperheroesEntitySample(1, 2, 3)
        val remoteSuperheroData = buildSuperheroesDtoSample(1, 2, 3)

        val vm = buildViewModelWith(
            localSuperheroData = localSuperheroData,
            remoteSuperheroData = remoteSuperheroData
        )

        vm.uiState.test {
            assertEquals(UiState(), awaitItem())
            val comicsView = awaitItem().comics
            assertEquals("Title 1", (comicsView[0] as ComicView).title)
            assertEquals("Title 2", (comicsView[1] as ComicView).title)
            assertEquals("Title 3", (comicsView[2] as ComicView).title)
            cancel()
        }
    }

    private fun buildViewModelWith(
        localSuperheroData: List<SuperHeroEntity> = emptyList(),
        remoteSuperheroData: List<SuperheroDto> = emptyList(),
        remoteComicsData: List<ComicDto> = emptyList(),
        localAttributionLink: String = "link"
    ): SuperheroesDetailViewModel {

        val superheroesRepository = buildSuperheroesRepositoryWith(
            localSuperheroData,
            remoteSuperheroData,
            remoteComicsData,
            localAttributionLink
        )

        val comicsRepository = buildComicsRepositoryWith(
            remoteSuperheroData,
            remoteComicsData
        )

        val getSuperheroDetailsUseCase = GetSuperheroDetailsUseCase(superheroesRepository)
        val getSuperheroComicsUseCase = GetSuperheroComicsUseCase(comicsRepository)

        return SuperheroesDetailViewModel(
            SavedStateHandle(),
            getSuperheroDetailsUseCase,
            getSuperheroComicsUseCase
        )
    }

}