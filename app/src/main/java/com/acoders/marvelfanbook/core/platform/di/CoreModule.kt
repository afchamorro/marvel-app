package com.acoders.marvelfanbook.core.platform.di

import com.acoders.marvelfanbook.core.platform.AppDispatcherProvider
import com.acoders.marvelfanbook.core.platform.DispatcherProvider
import com.acoders.marvelfanbook.core.platform.delegateadapter.RecycleViewDelegateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideAppDispatchers(): DispatcherProvider = AppDispatcherProvider()
}