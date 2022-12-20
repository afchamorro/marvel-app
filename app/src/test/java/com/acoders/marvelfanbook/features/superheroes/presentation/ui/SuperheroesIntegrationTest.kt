package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import app.cash.turbine.test
import com.acoders.marvelfanbook.features.buildSuperheroesDtoSample
import com.acoders.marvelfanbook.features.buildSuperheroesEntitySample
import com.acoders.marvelfanbook.features.buildSuperheroesRepositoryWith
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.superheroes.FakeNetworkConnectivityManager
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.FetchHeroesListUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetAttributionLinkUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroesUseCase
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroEntity
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesViewModel.UiState
import com.acoders.marvelfanbook.testrules.CoroutinesTestRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SuperheroesIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {

        val vm = buildViewModelWith()

        vm.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(dataList = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(dataList = emptyList(), loading = false), awaitItem())

            val superheroes = awaitItem().dataList
            assertEquals("Name 1", superheroes[0].name)
            assertEquals("Name 2", superheroes[1].name)
            assertEquals("Name 3", superheroes[2].name)

            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {

        val localSuperheroData = buildSuperheroesEntitySample(1, 2, 3)
        val remoteSuperheroData = buildSuperheroesDtoSample(4, 5, 6)

        val vm = buildViewModelWith(
            localSuperheroData = localSuperheroData,
            remoteSuperheroData = remoteSuperheroData
        )

        vm.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            val superheroesView = awaitItem().dataList
            assertEquals("Name 1", superheroesView[0].name)
            assertEquals("Name 2", superheroesView[1].name)
            assertEquals("Name 3", superheroesView[2].name)
            cancel()
        }
    }

    @Test
    fun `attribution info is loaded from server when local source`() = runTest {

        val vm = buildViewModelWith()

        vm.getAttributionLink()

        vm.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false), awaitItem())
            assertEquals(UiState(dataList = emptyList(), attributionLink = "link"), awaitItem())
            val attributionLink = awaitItem().attributionLink
            assertEquals("link", attributionLink)
            cancel()
        }

        runCurrent()
    }

    private fun buildViewModelWith(
        localSuperheroData: List<SuperHeroEntity> = emptyList(),
        remoteSuperheroData: List<SuperheroDto> = emptyList(),
        remoteComicsData: List<ComicDto> = emptyList(),
        localAttributionLink: String = "link"
    ): SuperheroesViewModel {

        val superheroesRepository = buildSuperheroesRepositoryWith(
            localSuperheroData,
            remoteSuperheroData,
            remoteComicsData,
            localAttributionLink
        )

        val fetchHeroesListUseCase = FetchHeroesListUseCase(superheroesRepository)
        val getSuperheroesUseCase = GetSuperheroesUseCase(superheroesRepository)
        val getAttributionLinkUseCase = GetAttributionLinkUseCase(superheroesRepository)
        val networkConnectivityManager = FakeNetworkConnectivityManager()

        return SuperheroesViewModel(
            fetchHeroesListUseCase,
            getSuperheroesUseCase,
            getAttributionLinkUseCase,
            networkConnectivityManager
        )
    }
}