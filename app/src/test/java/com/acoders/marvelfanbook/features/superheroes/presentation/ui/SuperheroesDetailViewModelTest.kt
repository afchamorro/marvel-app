package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.comics.domain.usecase.GetSuperheroComicsUseCase
import com.acoders.marvelfanbook.features.comics.presentation.model.ComicSkeletonView
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetailsUseCase
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesDetailViewModel.*
import com.acoders.marvelfanbook.features.superheroes.sampleComicsView
import com.acoders.marvelfanbook.features.superheroes.sampleSuperHeroView
import com.acoders.marvelfanbook.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SuperheroesDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    @Mock
    lateinit var getSuperheroDetailsUseCase: GetSuperheroDetailsUseCase

    @Mock
    lateinit var getSuperheroComicsUseCase: GetSuperheroComicsUseCase

    private lateinit var vm: SuperheroesDetailViewModel

    private val superhero = sampleSuperHeroView.copy(id = 1)
    private val comics = listOf(sampleComicsView.copy(id = 1), sampleComicsView.copy(id = 2))

    @Test
    fun `Comics are requested when UI screen starts`() = runTest {

        //GIVEN
        whenever(getSuperheroComicsUseCase(any())).thenReturn(comics.right())
        vm = SuperheroesDetailViewModel(
            savedStateHandle,
            getSuperheroDetailsUseCase,
            getSuperheroComicsUseCase
        )

        //WHEN
        runCurrent()

        //THEN
        verify(getSuperheroComicsUseCase).invoke(any())
    }

    @Test
    fun `Comics skeleton is shown when screen starts and hidden when it finishes requesting comics`() =
        runTest {

            //GIVEN
            whenever(getSuperheroComicsUseCase(any())).thenReturn(comics.right())
            vm = SuperheroesDetailViewModel(
                savedStateHandle,
                getSuperheroDetailsUseCase,
                getSuperheroComicsUseCase
            )

            //WHEN

            //THEN
            vm.uiState.test {
                assertEquals(UiState(comics = ComicSkeletonView.emptySkeleton), awaitItem())
                assertEquals(UiState(comics = comics), awaitItem())
                cancel()
            }
        }

    @Test
    fun `State is updated with error when comics are requested an error occurs`() =
        runTest {

            //GIVEN
            val error = Failure.Server(500)
            whenever(getSuperheroComicsUseCase(any())).thenReturn(error.left())
            vm = SuperheroesDetailViewModel(
                savedStateHandle,
                getSuperheroDetailsUseCase,
                getSuperheroComicsUseCase
            )

            //WHEN

            //THEN
            vm.uiState.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(error = error), awaitItem())
                cancel()
            }
        }

    @Test
    fun `State is updated with superheroes and progress indicator when is requested`() =
        runTest {

            //GIVEN
            whenever(getSuperheroDetailsUseCase(any())).thenReturn(flowOf(superhero))
            vm = SuperheroesDetailViewModel(
                savedStateHandle,
                getSuperheroDetailsUseCase,
                getSuperheroComicsUseCase
            )

            //WHEN
            vm.loadSuperheroDetail()

            //THEN
            vm.uiState.test {
                assertEquals(UiState(superheroView = null, loading = true), awaitItem())
                assertEquals(UiState(superheroView = superhero, loading = false), awaitItem())
                cancel()
            }
        }

}