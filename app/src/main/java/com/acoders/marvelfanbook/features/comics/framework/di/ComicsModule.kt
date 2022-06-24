package com.acoders.marvelfanbook.features.comics.framework.di

import com.acoders.marvelfanbook.features.comics.data.datasource.ComicsRemoteDataSource
import com.acoders.marvelfanbook.features.comics.data.repository.ComicsRepositoryImpl
import com.acoders.marvelfanbook.features.comics.domain.repository.ComicsRepository
import com.acoders.marvelfanbook.features.comics.framework.remote.ComicsAPIDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ComicsModule {

    @Binds
    abstract fun bindRepository(impl: ComicsRepositoryImpl): ComicsRepository

    @Binds
    abstract fun bindRemoteDataSource(remoteImpl: ComicsAPIDataSource): ComicsRemoteDataSource

}