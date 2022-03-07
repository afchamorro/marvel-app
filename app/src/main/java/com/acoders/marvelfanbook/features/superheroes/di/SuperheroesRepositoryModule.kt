package com.acoders.marvelfanbook.features.superheroes.di

import com.acoders.marvelfanbook.features.superheroes.domain.SuperheroesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class SuperheroesRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindRepository(impl: SuperheroesRepository.Network): SuperheroesRepository
}