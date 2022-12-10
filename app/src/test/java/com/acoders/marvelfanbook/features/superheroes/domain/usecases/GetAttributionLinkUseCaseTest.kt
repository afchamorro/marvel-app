package com.acoders.marvelfanbook.features.superheroes.domain.usecases

import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAttributionLinkUseCaseTest{

    private lateinit var getAttributionLinkUseCase: GetAttributionLinkUseCase

    @Mock
    lateinit var repository: SuperheroesRepository

    @Before
    fun setUp() {
        getAttributionLinkUseCase = GetAttributionLinkUseCase(repository)
    }

    @Test
    fun `Invoke calls superheroes repository and return flow attribution link`() = runTest {
        //GIVEN
        val attributionLink = flowOf("attribution link")
        whenever(repository.getAttributionLink()).thenReturn(attributionLink)

        //WHEN
        val result = getAttributionLinkUseCase()

        //THEN
        assertEquals(attributionLink, result)
    }
}