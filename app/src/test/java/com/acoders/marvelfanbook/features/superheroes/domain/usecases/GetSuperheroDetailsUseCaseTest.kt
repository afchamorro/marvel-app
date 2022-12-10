package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.data.repository.SUPERHERO_TEST_ID
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
class GetSuperheroDetailsUseCaseTest {

    private lateinit var getSuperheroDetailsUseCase: GetSuperheroDetailsUseCase

    @Mock
    lateinit var repository: SuperheroesRepository

    @Before
    fun setUp() {
        getSuperheroDetailsUseCase = GetSuperheroDetailsUseCase(repository)
    }

    @Test
    fun `Invoke calls superheroes repository and return flow of super hero`() = runTest {
        //GIVEN

        //WHEN
        getSuperheroDetailsUseCase(SUPERHERO_TEST_ID)

        //THEN
        verify(repository).superHero(SUPERHERO_TEST_ID)
    }
}