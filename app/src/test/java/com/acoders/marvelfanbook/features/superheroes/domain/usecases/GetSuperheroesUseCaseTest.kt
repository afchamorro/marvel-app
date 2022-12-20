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
class GetSuperheroesUseCaseTest{

    private lateinit var getSuperheroesUseCase: GetSuperheroesUseCase

    @Mock
    lateinit var repository: SuperheroesRepository

    @Before
    fun setUp() {
        getSuperheroesUseCase = GetSuperheroesUseCase(repository)
    }

    @Test
    fun `Invoke calls superheroes repository and return flow superheroes list`() = runTest {
        //GIVEN

        //WHEN
        getSuperheroesUseCase()

        //THEN
        verify(repository).superheroes
    }
}