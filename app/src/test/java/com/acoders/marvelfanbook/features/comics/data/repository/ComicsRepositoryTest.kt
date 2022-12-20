package com.acoders.marvelfanbook.features.comics.data.repository

import arrow.core.left
import arrow.core.right
import com.acoders.marvelfanbook.core.exception.Failure
import com.acoders.marvelfanbook.features.buildComicsDtoWrapperSample
import com.acoders.marvelfanbook.features.buildComicsListSample
import com.acoders.marvelfanbook.features.comics.data.datasource.ComicsRemoteDataSource
import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ComicsRepositoryTest {

    private lateinit var repository: ComicsRepository

    @Mock
    private lateinit var comicsRemoteDataSource: ComicsRemoteDataSource

    private val error = Failure.Server(500)

    private val comicsDto = buildComicsDtoWrapperSample()
    private val comics = buildComicsListSample(1, 2, 3)

    @Before
    fun setUp() {
        repository = ComicsRepositoryImpl(comicsRemoteDataSource)
    }

    @Test
    fun `comics are fetched from remote source`() = runTest {
        whenever(comicsRemoteDataSource.comicsByCharacter(any())).thenReturn(comicsDto.right())

        val result = repository.getSuperheroComics(any())

        assertEquals(comics.right(), result)
    }

    @Test
    fun `when error in superheroes comics return this error`() = runTest {
        whenever(comicsRemoteDataSource.comicsByCharacter(any())).thenReturn(error.left())

        val result = repository.getSuperheroComics(any())

        assertEquals(error.left(), result)
    }
}