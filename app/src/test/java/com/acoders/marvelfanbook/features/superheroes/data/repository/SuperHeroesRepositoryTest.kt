package com.acoders.marvelfanbook.features.superheroes.data.repository

import com.acoders.marvelfanbook.features.buildSuperheroesDtoWrapperSample
import com.acoders.marvelfanbook.features.superheroes.data.datasource.AttributionInfoLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.sampleSuperHero
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

const val SUPERHERO_TEST_ID = 7L

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SuperHeroesRepositoryTest {

    lateinit var repository: SuperheroesRepository

    @Mock
    lateinit var remoteDataSource: SuperHeroesRemoteDataSource

    @Mock
    lateinit var localDataSource: SuperHeroesLocalDataSource

    @Mock
    lateinit var attributionInfoLocalDataSource: AttributionInfoLocalDataSource

    private val localSuperheroes = flowOf(listOf(sampleSuperHero.copy(id = 1)))

    @Before
    fun setUp() {
        whenever(localDataSource.superheroes).thenReturn(localSuperheroes)
        repository =
            SuperHeroesRepositoryImpl(
                remoteDataSource,
                localDataSource,
                attributionInfoLocalDataSource
            )
    }

    @Test
    fun `Superheroes are taken from local data source if available`(): Unit = runBlocking {

        val result = localDataSource.superheroes

        assertEquals(localSuperheroes, result)
    }

    @Test
    fun `Superheroes are saved to local data source when it's empty`() =
        runTest {
            //GIVEN
            val result = buildSuperheroesDtoWrapperSample()
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever((remoteDataSource.superheroes())).thenReturn(result)

            //WHEN
            val superheroes = repository.fetchHeroesList()

            //THEN
            verify(localDataSource, times(1)).save(result.data.results.map { it.asDomainModel() })
            verify(
                attributionInfoLocalDataSource,
                times(1)
            ).saveAttributionLink(result.attributionHTML)
            assertEquals(null, superheroes)
        }

    @Test
    fun `Get superhero by id is done in local data source`(): Unit = runBlocking {

        val superhero = flowOf(sampleSuperHero.copy(id = SUPERHERO_TEST_ID))
        whenever(localDataSource.getSuperHeroesById(SUPERHERO_TEST_ID)).thenReturn(superhero)

        val result = repository.superHero(SUPERHERO_TEST_ID)

        assertEquals(superhero, result)
    }
}