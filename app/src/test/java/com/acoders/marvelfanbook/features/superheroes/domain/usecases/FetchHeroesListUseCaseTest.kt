package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FetchHeroesListUseCaseTest {

    private lateinit var fetchHeroesListUseCase: FetchHeroesListUseCase

    @Mock
    lateinit var repository: SuperheroesRepository

    @Before
    fun setUp() {
        fetchHeroesListUseCase = FetchHeroesListUseCase(repository)
    }

    @Test
    fun `Invoke calls superheroes repository`() = runTest {
        //GIVEN

        //WHEN
        fetchHeroesListUseCase()

        //THEN
        verify(repository).fetchHeroesList()
    }
}