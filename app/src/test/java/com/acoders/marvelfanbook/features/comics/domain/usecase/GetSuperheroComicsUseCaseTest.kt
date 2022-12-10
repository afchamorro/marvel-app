package com.acoders.marvelfanbook.features.comics.domain.usecase

import arrow.core.right
import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import com.acoders.marvelfanbook.features.superheroes.data.repository.SUPERHERO_TEST_ID
import com.acoders.marvelfanbook.features.superheroes.sampleComics
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetSuperheroComicsUseCaseTest {

    private lateinit var getSuperheroComicsUseCase: GetSuperheroComicsUseCase

    @Mock
    lateinit var repository: ComicsRepository

    @Before
    fun setUp() {
        getSuperheroComicsUseCase = GetSuperheroComicsUseCase(repository)
    }

    @Test
    fun `Invoke calls superheroes repository and return flow comics list`() = runTest {
        //GIVEN
        whenever(repository.getSuperheroComics(SUPERHERO_TEST_ID)).thenReturn(
            listOf(sampleComics.copy(id = 1)).right()
        )

        //WHEN
        getSuperheroComicsUseCase(SUPERHERO_TEST_ID)

        //THEN
        verify(repository).getSuperheroComics(SUPERHERO_TEST_ID)
    }

}