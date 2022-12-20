package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import app.cash.turbine.test
import com.acoders.marvelfanbook.core.platform.NetworkConnectivityManager
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.FetchHeroesListUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetAttributionLinkUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroesUseCase
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesViewModel.*
import com.acoders.marvelfanbook.features.superheroes.sampleSuperHeroView
import com.acoders.marvelfanbook.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SuperheroesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var fetchHeroesListUseCase: FetchHeroesListUseCase

    @Mock
    lateinit var getSuperheroesUseCase: GetSuperheroesUseCase

    @Mock
    lateinit var getAttributionLinkUseCase: GetAttributionLinkUseCase

    @Mock
    lateinit var networkConnectivityManager: NetworkConnectivityManager

    private val superheroes =
        listOf(sampleSuperHeroView.copy(id = 1), sampleSuperHeroView.copy(id = 2))

    private lateinit var vm: SuperheroesViewModel

    @Before
    fun setUp() {
        whenever(networkConnectivityManager.hasConnection).thenReturn(flowOf(true))
        whenever(getSuperheroesUseCase()).thenReturn(flowOf(superheroes))
        whenever(getAttributionLinkUseCase()).thenReturn(flowOf("link"))
        vm = SuperheroesViewModel(
            fetchHeroesListUseCase,
            getSuperheroesUseCase,
            getAttributionLinkUseCase,
            networkConnectivityManager
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {

        //WHEN

        //THEN
        vm.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(dataList = superheroes, loading = false), awaitItem())
            cancel()
        }
    }
    
    @Test
    fun `Superheroes are fetched when UI screen starts`() = runTest {

        //WHEN
        runCurrent()

        //THEN
        verify(fetchHeroesListUseCase).invoke()
    }

    @Test
    fun `State is updated whe attribution link is requested`() = runTest {

        //WHEN
        vm.getAttributionLink()

        vm.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(dataList = superheroes, loading = false), awaitItem())
            assertEquals(UiState(dataList = superheroes,attributionLink = "link"), awaitItem())
            cancel()
        }
    }

    @Test
    fun `State is updated when there is no connection`() =
        runTest {

            //GIVEN
            whenever(getSuperheroesUseCase()).thenReturn(flowOf(listOf()))
            whenever(networkConnectivityManager.hasConnection).thenReturn(flowOf(false))

            vm = SuperheroesViewModel(
                fetchHeroesListUseCase,
                getSuperheroesUseCase,
                getAttributionLinkUseCase,
                networkConnectivityManager
            )

            //WHEN
            runCurrent()

            //THEN
            vm.uiState.test {
                assertEquals(UiState(networkAvailable = false, dataList = arrayListOf()), awaitItem())
                cancel()
            }
        }

}