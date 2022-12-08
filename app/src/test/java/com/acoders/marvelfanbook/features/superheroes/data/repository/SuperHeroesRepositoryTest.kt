package com.acoders.marvelfanbook.features.superheroes.data.repository

import com.acoders.marvelfanbook.features.common.framework.remote.Paginated
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.data.datasource.AttributionInfoLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


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

    @Before
    fun setUp() {
        repository =
            SuperHeroesRepositoryImpl(
                remoteDataSource,
                localDataSource,
                attributionInfoLocalDataSource
            )
    }

    @Test
    fun `when local data source is empty fetch superheroes is called and result its stored`() =
        runTest {
            //GIVEN
            //TODO USE SUPERHERO SAMPLE
            val result = PaginatedWrapper<SuperheroDto>(data = Paginated(results = listOf()))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever((remoteDataSource.superheroes())).thenReturn(result)

            //WHEN
            val superheros = repository.fetchHeroesList()

            //THEN
            verify(localDataSource, times(1)).save(result.data.results.map { it.asDomainModel() })
            verify(attributionInfoLocalDataSource, times(1)).saveAttributionLink(result.attributionHTML)
            assertEquals(null, superheros)

        }
}