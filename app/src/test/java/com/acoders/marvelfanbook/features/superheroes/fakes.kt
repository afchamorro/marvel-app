package com.acoders.marvelfanbook.features.superheroes

import com.acoders.marvelfanbook.core.platform.NetworkConnectivityManager
import com.acoders.marvelfanbook.features.buildComicsDtoWrapperSample
import com.acoders.marvelfanbook.features.buildSuperheroesDtoWrapperSample
import com.acoders.marvelfanbook.features.comics.framework.model.ComicDto
import com.acoders.marvelfanbook.features.common.framework.remote.PaginatedWrapper
import com.acoders.marvelfanbook.features.superheroes.data.datasource.AttributionInfoLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroDao
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroEntity
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperheroDto
import com.acoders.marvelfanbook.framework.remote.api.MarvelEndpoints
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeSuperheroDao(superheroes: List<SuperHeroEntity> = emptyList()) : SuperHeroDao {

    private val inMemorySuperheroes = MutableStateFlow(superheroes)
    private lateinit var findSuperheroFlow: MutableStateFlow<SuperHeroEntity>

    override suspend fun saveSuperHeroes(heroesList: List<SuperHeroEntity>) {
        inMemorySuperheroes.value = heroesList

        if (::findSuperheroFlow.isInitialized) {
            heroesList.firstOrNull { it.id == findSuperheroFlow.value.id }
                ?.let { findSuperheroFlow.value = it }
        }
    }

    override fun getSuperHeroesList(): Flow<List<SuperHeroEntity>> = inMemorySuperheroes

    override fun getSuperHeroesById(id: Long): Flow<SuperHeroEntity> {
        findSuperheroFlow = MutableStateFlow(inMemorySuperheroes.value.first { it.id == id })
        return findSuperheroFlow
    }

    override suspend fun numHeroes(): Int = inMemorySuperheroes.value.size
}

class FakeRemoteService(
    private val superheroes: List<SuperheroDto> = emptyList(),
    private val comics: List<ComicDto> = emptyList()
) : MarvelEndpoints {

    override suspend fun getSuperheroes(
        series: String,
        limit: Int
    ): PaginatedWrapper<SuperheroDto> = buildSuperheroesDtoWrapperSample(superheroes)

    override suspend fun getSuperheroComics(characterId: Long): PaginatedWrapper<ComicDto> =
        buildComicsDtoWrapperSample(comics)

}

class FakeAttributionInfoPreferencesDataSource(link: String) : AttributionInfoLocalDataSource {

    private val inMemoryLink = MutableStateFlow(link)

    override fun getAttributionLink(): Flow<String> = inMemoryLink

    override suspend fun saveAttributionLink(link: String) {
        inMemoryLink.value = link
    }

}

class FakeNetworkConnectivityManager : NetworkConnectivityManager {
    override val hasConnection: Flow<Boolean> = flowOf(true)
}