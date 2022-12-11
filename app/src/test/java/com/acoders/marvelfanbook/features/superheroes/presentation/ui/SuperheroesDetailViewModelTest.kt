package com.acoders.marvelfanbook.features.superheroes.presentation.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.right
import com.acoders.marvelfanbook.features.comics.domain.usecase.GetSuperheroComicsUseCase
import com.acoders.marvelfanbook.features.superheroes.domain.usecases.GetSuperheroDetailsUseCase
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesDetailViewModel.*
import com.acoders.marvelfanbook.features.superheroes.sampleComicsView
import com.acoders.marvelfanbook.features.superheroes.sampleSuperHeroView
import com.acoders.marvelfanbook.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
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

    @Before
    fun setup() = runTest {
        whenever(getSuperheroDetailsUseCase(any())).thenReturn(flowOf(superhero))
        whenever(getSuperheroComicsUseCase(any())).thenReturn(comics.right())
        vm = SuperheroesDetailViewModel(
            savedStateHandle,
            getSuperheroDetailsUseCase,
            getSuperheroComicsUseCase
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.uiState.test {
            assertEquals(UiState(comics = comics), awaitItem())
            cancel()
        }
    }
}