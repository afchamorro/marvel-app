package com.acoders.marvelfanbook.features.superheroes.framework.di

import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesLocalDataSource
import com.acoders.marvelfanbook.features.superheroes.data.datasource.SuperHeroesRemoteDataSource
import com.acoders.marvelfanbook.features.superheroes.data.repository.SuperHeroesRepositoryImpl
import com.acoders.marvelfanbook.features.superheroes.domain.repository.SuperheroesRepository
import com.acoders.marvelfanbook.features.superheroes.framework.database.SuperHeroesLocalDataSourceImpl
import com.acoders.marvelfanbook.features.superheroes.framework.remote.SuperHeroesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class SuperheroesRepositoryModule {

    @Binds
    abstract fun bindRepository(impl: SuperHeroesRepositoryImpl): SuperheroesRepository

    @Binds
    abstract fun bindRemoteDataSource(remoteImpl: SuperHeroesRemoteDataSourceImpl): SuperHeroesRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(localImpl: SuperHeroesLocalDataSourceImpl): SuperHeroesLocalDataSource

}